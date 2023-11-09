package com.xunlei.framework.validate;

import com.xunlei.framework.validate.impl.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 表单验证器封装，能解析以下几种验证方式
 * <ul>
 * <li>@NotNull：不能为Null</li>
 * <li>@NotEmpty: 不仅不为Null,内容不能为空</li>
 * <li>@Length：限制字符串长度或数组长度</li>
 * <li>@Url：是一个有效格式的URL</li>
 * <li>@Mobile：是一个11位手机号码</li>
 * <li> and more ...</li>
 * </ul>
 */
public class ObjectValidator {

    // 验证器实例
    private static final Map<Class<?>, ConstraintValidator> validatorMap;
    // 等待验证的对象Class缓存，Key: Wait Valid Object Class
    private static final ConcurrentHashMap<Class<?>, Future<List<WaitValidField>>> waitValidFieldCacheMap = new ConcurrentHashMap<>();

    static {
        validatorMap = new HashMap<>();
        validatorMap.put(XLLengthValidator.class, new XLLengthValidator());
        validatorMap.put(XLMobileValidator.class, new XLMobileValidator());
        validatorMap.put(XLNotEmptyValidator.class, new XLNotEmptyValidator());
        validatorMap.put(XLNotNullValidator.class, new XLNotNullValidator());
        validatorMap.put(XLUrlValidator.class, new XLUrlValidator());
        validatorMap.put(XLRangeValidator.class, new XLRangeValidator());
    }

    /**
     * 通过该方法注册更多的验证器
     */
    public void registerValidator(ConstraintValidator impl) {
        if (impl != null) {
            validatorMap.put(impl.getClass(), impl);
        }
    }


    /**
     * 验证器核心方法，该接口
     *
     * @param waitValidClass  等待验证对象的Class类型，原来可以不传，但如果对象值为Null，就没办法去验证了。
     * @param waitValidObject 通常传入的是一个对象比如：Form，而不是单一的一个验证参数值
     */
    public void validate(Class<?> waitValidClass, Object waitValidObject) throws ValidException {
        List<WaitValidField> validFields = get(waitValidClass);
        if (validFields == null) {
            return;
        }

        // 逐个开始验证
        for (WaitValidField validField : validFields) {
            ValidResult vr = this.doValidField(validField, waitValidObject);
            if (!vr.isPass()) {
                throw new ValidException(vr.getMessage());
            }
        }

    }

    /**
     * Just verify a field
     */
    private ValidResult doValidField(WaitValidField validField, Object waitValidObject) throws ValidException {
        // 属性值
        Object rawValue = null;
        if (waitValidObject != null) {
            validField.getClassField().setAccessible(true);
            try {
                rawValue = validField.getClassField().get(waitValidObject);
            } catch (IllegalAccessException e) {
                throw new ValidException("无法读取属性" + validField.getClassField().getName() + "值", e);
            }
        }
        // 注解配置
        Annotation config = validField.getValidAnnotation();
        // 验证器
        Class<?> validClass = validField.getValidClass();
        ConstraintValidator fieldValidator = validatorMap.get(validClass);
        if (fieldValidator == null) {
            throw new ValidException("验证器" + validClass.getName() + "未注册");
        }
        String fieldName = validField.getClassField().getName();
        // 开始验证
        return fieldValidator.isValid(config, fieldName, rawValue, waitValidObject);
    }


    /**
     * Get Valid Fields From Cache First
     */
    private List<WaitValidField> get(final Class<?> waitValidClass) throws ValidException {
        // 从缓存中获取待验证的字段配置
        Future<List<WaitValidField>> future = waitValidFieldCacheMap.get(waitValidClass);
        if (future == null) {
            // future callback
            Callable<List<WaitValidField>> eval = new Callable<List<WaitValidField>>() {
                @Override
                public List<WaitValidField> call() {
                    return doTransformValidFields(waitValidClass);
                }
            };

            FutureTask<List<WaitValidField>> futureTask = new FutureTask<>(eval);
            // putIfAbsent 防止并发
            future = waitValidFieldCacheMap.putIfAbsent(waitValidClass, futureTask);
            if (future == null) {
                future = futureTask;
                futureTask.run();
            }
        }

        try {
            return future.get();
        } catch (InterruptedException e) {
            waitValidFieldCacheMap.remove(waitValidClass);
            throw new ValidException(e);
        } catch (ExecutionException e) {
            waitValidFieldCacheMap.remove(waitValidClass);
            throw new ValidException("Load [" + waitValidClass.getName() + "] Valid Config Error", e);
        }
    }


    /**
     * 反射解析Class中需要验证的字段
     */
    private List<WaitValidField> doTransformValidFields(Class<?> waitValidClass) {
        // 需要验证的字段列表
        List<WaitValidField> waitFieldList = new ArrayList<>();

        // 逐个进行判断
        Field[] fields = waitValidClass.getDeclaredFields();
        for (Field waitValidField : fields) {
            // 检查是否配置了注解
            Annotation[] annotations = waitValidField.getAnnotations();
            for (Annotation validAnnotation : annotations) {
                if (validAnnotation.annotationType().isAnnotationPresent(XLConstraint.class)) {
                    // 验证器
                    XLConstraint lmc = validAnnotation.annotationType().getAnnotation(XLConstraint.class);
                    Class<? extends ConstraintValidator> validClass = lmc.validatedBy();

                    // 封装到集合中缓存起来
                    waitFieldList.add(new WaitValidField(waitValidField, validClass, validAnnotation));
                }
            }
        }

        return waitFieldList;
    }

    /**
     * 需要验证字段封装
     */
    private class WaitValidField {
        // 待验证的Field
        private Field classField;
        // 验证器Class
        private Class<? extends ConstraintValidator> validClass;
        // 注解实例
        private Annotation validAnnotation;

        private WaitValidField(Field validField, Class<? extends ConstraintValidator> validClass, Annotation validAnnotation) {
            this.classField = validField;
            this.validClass = validClass;
            this.validAnnotation = validAnnotation;
        }

        private Field getClassField() {
            return classField;
        }

        private Class<? extends ConstraintValidator> getValidClass() {
            return validClass;
        }

        private Annotation getValidAnnotation() {
            return validAnnotation;
        }
    }

}
