package com.yexin.httplib.request;


import com.yexin.httplib.utils.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * author: zengven
 * date: 2019/4/3 17:42
 * desc: TODO
 */
public interface IBuildParamter {

    Object build(String httpMethod, LinkedList<Pair<Type, Annotation>> paramterPairs, Object[] args);

}
