package com.inloadings.liteWarpGUI.util.commandImpl.Super;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ICommandInfo {
    String name();
    String description();
    String[] usage();
    String[] aliases() default {};
    String perm() default "";
    String permMessage() default "You do not have permission to use this command";



}
