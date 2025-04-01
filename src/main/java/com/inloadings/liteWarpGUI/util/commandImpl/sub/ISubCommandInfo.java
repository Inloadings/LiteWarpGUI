package com.inloadings.liteWarpGUI.util.commandImpl.sub;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ISubCommandInfo {
    String usage();
    String[] params();
    String perm() default "";
}
