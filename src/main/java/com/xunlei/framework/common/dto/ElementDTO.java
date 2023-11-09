package com.xunlei.framework.common.dto;

/**
 * 元素信息
 */
public class ElementDTO<E> {

    private int index;
    private E element;

    public static <E> ElementDTO build(int index, E element) {
        ElementDTO dto = new ElementDTO();
        dto.setIndex(index);
        dto.setElement(element);
        return dto;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public E getElement() {
        return element;
    }

    public void setElement(E element) {
        this.element = element;
    }
}
