package com.zzq.my.jetpacks.common.utils

import java.lang.reflect.ParameterizedType

 /*
   * ================================================
   * 描述：通用工具类
   * 作者：zhuangzeqin
   * 时间: 2021/5/24-10:54
   * 邮箱：zzq@eeepay.cn
   * 备注:
   * ----------------------------------------------------------------
   * You never know what you can do until you try !
   *      _              _           _     _   ____  _             _ _
   *     / \   _ __   __| |_ __ ___ (_) __| | / ___|| |_ _   _  __| (_) ___
   *    / _ \ | '_ \ / _` | '__/ _ \| |/ _` | \___ \| __| | | |/ _` | |/ _ \
   *   / ___ \| | | | (_| | | | (_) | | (_| |  ___) | |_| |_| | (_| | | (_) |
   *  /_/   \_\_| |_|\__,_|_|  \___/|_|\__,_| |____/ \__|\__,_|\__,_|_|\___/
   *
   * 签名：最痛苦的事不是我失败了，而是我本可以.--zzq
   * ----------------------------------------------------------------
   * ================================================
   */
object CommonUtil {
    /**
     * 通过反射，自动实例化泛型中的class对象
     */
    fun <T> getClass(t: Any): Class<T> {
        // 通过反射 获取父类泛型 (T) 对应 Class类
        return (t.javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0]
                as Class<T>
    }
}