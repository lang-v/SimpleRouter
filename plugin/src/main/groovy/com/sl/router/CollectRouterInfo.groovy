package com.sl.router

import com.android.build.api.transform.*
import com.android.utils.FileUtils

import java.util.jar.JarEntry

/**
 * 收集经过注解处理器输出的各类路由信息
 * router.properties
 *
 */
class CollectClassInfo extends Transform {

    private TransformOutputProvider outputProvider

    @Override
    String getName() {
        return "CollectClassInfo"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        Set<QualifiedContent.ContentType> types = new HashSet<>()
        types.add(QualifiedContent.DefaultContentType.CLASSES)
//        types.add(QualifiedContent.DefaultContentType.RESOURCES)
        return types
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        Set<QualifiedContent.Scope> scopes = new HashSet<>();
        scopes.add(QualifiedContent.Scope.PROJECT)
        scopes.add(QualifiedContent.Scope.SUB_PROJECTS)
        return scopes
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)
        Collection<QualifiedContent> inputs = transformInvocation.inputs.collect({ TransformInput input ->
            input.jarInputs + input.directoryInputs
        }).flatten()

        outputProvider = transformInvocation.getOutputProvider()
        outputProvider.deleteAll()

        inputs.each {
            copy(it)
        }
    }

    void copy(QualifiedContent input) {
        def isFile = input instanceof JarInput
        def format = isFile? Format.JAR : Format.DIRECTORY
        input
        def dest = outputProvider.getContentLocation(input.file.absolutePath, input.contentTypes, input.scopes, format)

        println("CollectClassInfo copy "+input.file.absolutePath + " to " +dest.absolutePath)

        if (isFile) {
            FileUtils.copyFile(input.file, dest)
        } else {
            FileUtils.copyDirectory(input.file, dest)
        }

    }
}
