package com.sl.router

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.sl.router.util.FileUtil
import org.gradle.api.Project
import org.gradle.api.file.FileTree

import java.nio.CharBuffer
import java.util.jar.JarEntry

/**
 * 收集经过注解处理器输出的各类路由信息
 * router.properties
 *
 */
class CollectRouterInfo extends Transform {

    private Project project
    private TransformOutputProvider outputProvider
    private File collectPath


    public CollectRouterInfo(Project project) {
        this.project = project
        collectPath = project.file(project.getBuildDir().toString() + File.separator + "generated" + File.separator + "router-collect")
        ln(collectPath.toString())
        if (collectPath.exists()) {
            collectPath.deleteDir()
            collectPath.mkdirs()
        } else {
            collectPath.mkdirs()
        }
    }

    @Override
    String getName() {
        return "CollectRouterInfo"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {

        outputProvider = transformInvocation.getOutputProvider()

        int count = 0
        StringBuilder builder = new StringBuilder()
        transformInvocation.inputs.each { input ->
            input.jarInputs.each { jar ->
                FileTree tree = project.zipTree(jar.file)

                // 从jar中提取路由信息
                def routerInfo = tree.matching {
                    include "Router.properties"
                }.getFiles()

                if (routerInfo.size() != 0) {
                    FileReader reader = new FileReader(routerInfo[0])
                    def lines = reader.readLines()
                    lines.each {
                        builder.append(it)
                    }
                }

//                File dest = project.file(collectPath.toString()+File.separator+jar.name+"-"+routerInfo.name)
//                FileUtil.copyFile(routerInfo.toPath(),dest.toPath())

                count++
                copy(jar)
            }
            input.directoryInputs.each { dir ->
                dir.file.listFiles().each {
                    ln(it.absolutePath)
                }
                copy(dir)
            }
        }
        ln(builder.toString())

//        Collection<QualifiedContent> inputs = transformInvocation.inputs.collect({ TransformInput input ->
//            input.jarInputs + input.directoryInputs
//        }).flatten()
//
//        outputProvider = transformInvocation.getOutputProvider()
//        outputProvider.deleteAll()
//
//        inputs.each {
//            copy(it)
//        }
    }

    void copy(QualifiedContent input) {
        def isFile = input instanceof JarInput
        def format = isFile ? Format.JAR : Format.DIRECTORY
        input
        def dest = outputProvider.getContentLocation(input.file.absolutePath, input.contentTypes, input.scopes, format)

//        println("CollectClassInfo copy " + input.file.absolutePath + " to " + dest.absolutePath)

        if (isFile) {
            FileUtils.copyFile(input.file, dest)
        } else {
            FileUtils.copyDirectory(input.file, dest)
        }

    }

    void ln(String msg) {
//        logger.debug(msg)
        project.println(getName() + ">" + msg)
    }
}
