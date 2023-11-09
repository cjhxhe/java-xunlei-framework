package com.xunlei.framework.common.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 算法工具类
 *
 * @author maoqinying
 */
public class AlgorithmUtil {

    public static List<List<Integer>> combinationSum(List<Integer> candidates, int target) {

        ArrayList<List<Integer>> res = new ArrayList<List<Integer>>();

        if (candidates == null || candidates.size() == 0) {
            return res;
        }
        Collections.sort(candidates);
        LinkedList<Integer> out = new LinkedList<>();
        combinationSumDFS(res, candidates, target, 0, out);
        return res;
    }

    private static void combinationSumDFS(ArrayList<List<Integer>> res, List<Integer> candidates, int target, int start, LinkedList<Integer> out) {
        if (target < 0) {
            return;
        } else if (target == 0) {
            res.add((LinkedList<Integer>) out.clone());
            return;
        }

        for (int i = start; i < candidates.size(); ++i) {
            out.addLast(candidates.get(i));
            combinationSumDFS(res, candidates, target - candidates.get(i), i, out);
            out.removeLast();
        }
    }
}
