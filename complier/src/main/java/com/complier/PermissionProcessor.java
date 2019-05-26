package com.complier;

import com.annotation.NeedsPermission;
import com.annotation.OnNeverAskAgain;
import com.annotation.OnPermissionDenied;
import com.annotation.OnShowRationale;
import com.google.auto.service.AutoService;

import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

//通过auto-service中的@AutoService可以自动生成AutoService 注解处理器是Google开发的
//用来生成 META-INF/services/javax.annotation.processing.Processor文件
@AutoService(Processor.class)
public class PermissionProcessor extends AbstractProcessor {

    //用来报告错误，警示和其他提示信息
    private Messager messager;
    //Elements中包含用于操作Element的工具方法
    private Elements elementUtils;
    //Filter用来创建新的源文件，class文件以及辅助文件
    private Filer filter;
    //Types中包含用于操作TypeMirror的工具方法
    private Types typeUtils;

    /**
     * 该方法主要用于一些初始化的操作，通过该方法的参数ProcessingEnvironment可以
     * 获取一系列有用的工具类
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        typeUtils = processingEnvironment.getTypeUtils();
        elementUtils = processingEnvironment.getElementUtils();
        filter = processingEnvironment.getFiler();
        messager = processingEnvironment.getMessager();
    }

    /**
     * 添加支持注解的类型
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(NeedsPermission.class.getCanonicalName());
        types.add(OnNeverAskAgain.class.getCanonicalName());
        types.add(OnPermissionDenied.class.getCanonicalName());
        types.add(OnShowRationale.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        // 返回此注释 Processor 支持的最新的源版本，该方法可以通过注解@SupportedSourceVersion指定
        return SourceVersion.latest();
    }

    /**
     * 注解处理器的核心方法，处理具体的注解，生成java文件
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        //获取Activity中所有带NeedsPermission注解的方法
        Set<? extends Element> needsPermissionSet = roundEnvironment.getElementsAnnotatedWith(NeedsPermission.class);
        //保存键值对，key是com.xx.xx.xxActivity value是所有带NeedsPermission注解的方法集合
        Map<String, List<ExecutableElement>> needsPermissionMap = new HashMap<>();

        //遍历所有带NeedsPermission注解的方法
        for (Element element : needsPermissionSet) {
            //转成原始属性元素（结构体元素）
            ExecutableElement executableElement = (ExecutableElement) element;
            //通过属性元素获取它所属的类名，com.xx.xx.xxActivity
            String clazzName = getClazzName(executableElement);
            //从缓存集合中获取类中所有带NeedsPermission注解方法的集合
            List<ExecutableElement> list = needsPermissionMap.get(clazzName);
            if (list == null) {
                list = new ArrayList<>();
                //先加入map集合，易用变量list可以动态改变值
                needsPermissionMap.put(clazzName, list);
            }
            //将类中所有带NeedsPermission注解的方法加入到list集合
            list.add(executableElement);
            System.out.println("NeedsPermission executableElement >>> " + executableElement.getSimpleName().toString());
        }

        //获取Activity中所有带OnNeverAskAgain注解的方法
        Set<? extends Element> onNeverAskAgainSet = roundEnvironment.getElementsAnnotatedWith(OnNeverAskAgain.class);
        //保存键值对，key是com.xx.xx.xxActivity value是所有带NeedsPermission注解的方法集合
        Map<String, List<ExecutableElement>> onNeverAskAgainMap = new HashMap<>();

        //遍历所有带OnNeverAskAgain注解的方法
        for (Element element : onNeverAskAgainSet) {
            //转成原始属性元素（结构体元素）
            ExecutableElement executableElement = (ExecutableElement) element;
            //通过属性元素获取它所属的类名，com.xx.xx.xxActivity
            String clazzName = getClazzName(executableElement);
            //从缓存集合中获取类中所有带OnNeverAskAgain注解方法的集合
            List<ExecutableElement> list = onNeverAskAgainMap.get(clazzName);
            if (list == null) {
                list = new ArrayList<>();
                //先加入map集合，易用变量list可以动态改变值
                onNeverAskAgainMap.put(clazzName, list);
            }
            //将类中所有带OnNeverAskAgain注解的方法加入到list集合
            list.add(executableElement);
            System.out.println("OnNeverAskAgain executableElement >>> " + executableElement.getSimpleName().toString());
        }

        //获取Activity中所有带OnPermissionDenied注解的方法
        Set<? extends Element> onPermissionDeniedSet = roundEnvironment.getElementsAnnotatedWith(OnPermissionDenied.class);
        //保存键值对，key是com.xx.xx.xxActivity value是所有带OnPermissionDenied注解的方法集合
        Map<String, List<ExecutableElement>> onPermissionDeniedMap = new HashMap<>();

        //遍历所有带OnPermissionDenied注解的方法
        for (Element element : onPermissionDeniedSet) {
            //转成原始属性元素（结构体元素）
            ExecutableElement executableElement = (ExecutableElement) element;
            //通过属性元素获取它所属的类名，com.xx.xx.xxActivity
            String clazzName = getClazzName(executableElement);
            //从缓存集合中获取类中所有带OnPermissionDenied注解方法的集合
            List<ExecutableElement> list = onPermissionDeniedMap.get(clazzName);
            if (list == null) {
                list = new ArrayList<>();
                //先加入map集合，易用变量list可以动态改变值
                onPermissionDeniedMap.put(clazzName, list);
            }
            //将类中所有带OnPermissionDenied注解的方法加入到list集合
            list.add(executableElement);
            System.out.println("OnPermissionDenied executableElement >>> " + executableElement.getSimpleName().toString());
        }

        //获取Activity中所有带OnShowRationale注解的方法
        Set<? extends Element> onShowRationaleSet = roundEnvironment.getElementsAnnotatedWith(OnShowRationale.class);
        //保存键值对，key是com.xx.xx.xxActivity value是所有带OnShowRationale注解的方法集合
        Map<String, List<ExecutableElement>> onShowRationaleMap = new HashMap<>();

        //遍历所有带OnShowRationale注解的方法
        for (Element element : onShowRationaleSet) {
            //转成原始属性元素（结构体元素）
            ExecutableElement executableElement = (ExecutableElement) element;
            //通过属性元素获取它所属的类名，com.xx.xx.xxActivity
            String clazzName = getClazzName(executableElement);
            //从缓存集合中获取类中所有带OnShowRationale注解方法的集合
            List<ExecutableElement> list = onShowRationaleMap.get(clazzName);
            if (list == null) {
                list = new ArrayList<>();
                //先加入map集合，易用变量list可以动态改变值
                onShowRationaleMap.put(clazzName, list);
            }
            //将类中所有带OnShowRationale注解的方法加入到list集合
            list.add(executableElement);
            System.out.println("OnShowRationale executableElement >>> " + executableElement.getSimpleName().toString());
        }

        //书写自动生成类~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        // 获取类完整的字符串类名（包名 + 类名）
        for (String clazzName : needsPermissionMap.keySet()) {
            // 获取"com.xxx.xxx.xxxActivity"中所有控件方法的集合
            List<ExecutableElement> needsPermissionElements = needsPermissionMap.get(clazzName);
            List<ExecutableElement> onNeverAskAgainElements = onNeverAskAgainMap.get(clazzName);
            List<ExecutableElement> onPermissionDeniedElements = onPermissionDeniedMap.get(clazzName);
            List<ExecutableElement> onShowRationaleElements = onShowRationaleMap.get(clazzName);

            final String CLASS_SUFFIX = "$Permissions";

            try {
                //创建一个新的源文件，并返回一个对象以允许写入它
                JavaFileObject javaFileObject = filter.createSourceFile(clazzName + CLASS_SUFFIX);
                //通过方法标签获取包名标签（任意一个属性标签的父节点都是同一个包名）
                String packageName = getPackageName(needsPermissionElements.get(0));
                //定义Writer对象，开启类生成过程
                Writer writer = javaFileObject.openWriter();

                // 类名：xxxActivity$Permissions，不是com.xxx.xxx.xxxActivity$Permissions
                // 通过属性元素获取它所属的xxxActivity类名，再拼接后结果为：xxxActivity$Permissions
                String clazzSimpleName = needsPermissionElements.get(0).getEnclosingElement().getSimpleName().toString() + CLASS_SUFFIX;
                System.out.println("clazzSimpleName >>> " + clazzSimpleName + "\nclazzSimpleName >>> " + clazzSimpleName);

                System.out.println("开始生成 ----------------------------------->");
                //生成包
                writer.write("package " + packageName + ";\n");
                writer.write("import com.library.listener.RequestPermission;\n");
                writer.write("import com.library.listener.PermissionRequest;\n");
                writer.write("import com.library.utils.PermissionUtils;\n");
                writer.write("import android.support.v7.app.AppCompatActivity;\n");
                writer.write("import android.support.v4.app.ActivityCompat;\n");
                writer.write("import android.support.annotation.NonNull;\n");
                writer.write("import java.lang.ref.WeakReference;\n");

                //生成类
                writer.write("public class " + clazzSimpleName
                        + " implements RequestPermission<" + clazzName + ">{\n");
                //生成常亮属性
                writer.write("private static final int REQUEST_CODE = 666;\n");
                writer.write("private static String[] PERMISSION;\n");

                //生成requestPermission方法
                writer.write("public void requestPermission(" + clazzName + " target, String[] permissions) {\n");
                writer.write("this.PERMISSION = permissions;\n");
                writer.write("if(PermissionUtils.hasSelfPermissions(target,permissions)){\n");

                //循环生成xxxActivity每个权限申请方法
                for (ExecutableElement executableElement : needsPermissionElements) {
                    //获取方法名
                    String methodName = executableElement.getSimpleName().toString();
                    //调用申请权限方法
                    writer.write("target." + methodName + "();\n");
                }

                writer.write("} else if (PermissionUtils.shouldShowRequestPermissionRationale(target,permissions)){\n");
                //循环生成xxxActivity每个提示用户为何要开启权限方法
                if (onShowRationaleElements != null && !onShowRationaleElements.isEmpty()) {
                    for (ExecutableElement executableElement : onShowRationaleElements) {
                        String methodName = executableElement.getSimpleName().toString();
                        writer.write("target." + methodName + "(new PermissionRequestImpl(target));\n");
                    }
                }
                writer.write("} else {\n");
                writer.write("ActivityCompat.requestPermissions(target, permissions, REQUEST_CODE);\n}\n}\n");

                //生成onRequestPermissionResult方法
                writer.write("public void onRequestPermissionResult(" + clazzName + " target, int requestCode, @NonNull int[] grantResults) {");
                writer.write("switch(requestCode) {\n");
                writer.write("case REQUEST_CODE:\n");
                writer.write("if (PermissionUtils.verifyPermissions(grantResults)) {\n");

                //循环生成每个权限申请方法
                for (ExecutableElement executableElement : needsPermissionElements) {
                    // 获取方法名
                    String methodName = executableElement.getSimpleName().toString();
                    // 调用申请权限方法
                    writer.write("target." + methodName + "();\n");
                }

                writer.write("} else if (!PermissionUtils.shouldShowRequestPermissionRationale(target, PERMISSION)) {\n");


                // 循环生成xxxActivity每个不再询问后的提示
                if (onNeverAskAgainElements != null && !onNeverAskAgainElements.isEmpty()) {
                    for (ExecutableElement executableElement : onNeverAskAgainElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 调用不再询问后的提示
                        writer.write("target." + methodName + "();\n");
                    }
                }
                writer.write("} else {\n");

                // 循环生成xxxActivity每个拒绝时的提示方法
                if (onPermissionDeniedElements != null && !onPermissionDeniedElements.isEmpty()) {
                    for (ExecutableElement executableElement : onPermissionDeniedElements) {
                        // 获取方法名
                        String methodName = executableElement.getSimpleName().toString();
                        // 调用拒绝时的提示方法
                        writer.write("target." + methodName + "();\n");
                    }
                }

                writer.write("}\nbreak;\ndefault:\nbreak;\n}\n}\n");

                // 生成接口实现类：PermissionRequestImpl implements PermissionRequest
                writer.write("private static final class PermissionRequestImpl implements PermissionRequest {\n");
                writer.write("private final WeakReference<" + clazzName + "> weakTarget;\n");
                writer.write("private PermissionRequestImpl(" + clazzName + " target) {\n");
                writer.write("this.weakTarget = new WeakReference(target);\n}\n");
                writer.write("public void proceed() {\n");
                writer.write(clazzName + " target = (" + clazzName + ")this.weakTarget.get();\n");
                writer.write("if (target != null) {\n");
                writer.write("ActivityCompat.requestPermissions(target, PERMISSION, REQUEST_CODE);\n}\n}\n}\n");

                // 最后结束标签，类生成完成
                writer.write("\n}");
                System.out.println("结束 ----------------------------------->");
                writer.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return false;
    }

    private String getClazzName(ExecutableElement executableElement) {
        //通过方法标签获取类名标签，再通过类名标签获取包名标签
        String packageName = getPackageName(executableElement);
        // 通过方法标签获取类名标签
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        // 完整字符串拼接：com.xxx.xxx+ "." + xxxActivity
        return packageName + "." + typeElement.getSimpleName().toString();
    }

    private String getPackageName(ExecutableElement executableElement) {
        // 通过方法标签获取类名标签
        TypeElement typeElement = (TypeElement) executableElement.getEnclosingElement();
        // 通过类名标签获取包名标签
        String packageName = processingEnv.getElementUtils().getPackageOf(typeElement).getQualifiedName().toString();
        System.out.println("packageName >>>  " + packageName);
        return packageName;
    }
}
