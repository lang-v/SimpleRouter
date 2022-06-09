/*
 * This Groovy source file was generated by the Gradle 'init' task.
 */
package com.sl.router

import org.gradle.testfixtures.ProjectBuilder
import org.gradle.api.Project
import spock.lang.Specification

/**
 * A simple unit test for the 'com.sl.router.greeting' plugin.
 */
class SimpleRouterPluginTest extends Specification {
    def "plugin registers task"() {
        given:
        def project = ProjectBuilder.builder().build()

        when:
        project.plugins.apply("com.sl.router.greeting")

        then:
        project.tasks.findByName("greeting") != null
    }
}
