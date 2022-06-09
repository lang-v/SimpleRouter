package com.sl.router

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class RouterPlugin implements Plugin<Project> {

    private Project project

    @Override
    void apply(Project project) {
        this.project = project
        ln("init start")
        if (project.plugins.hasPlugin('com.android.application')) {
            ln('is application main project')
        }else if (project.plugins.hasPlugin('com.android.library')){
            ln("is library")
        }
        project.getExtensions().getByType(AppExtension).registerTransform(new CollectClassInfo())
        ln("init end")
    }

    void ln(String log) {
        project.println("RouterPlugin> "+log)
    }
}
