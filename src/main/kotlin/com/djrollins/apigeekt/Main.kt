package com.djrollins.apigeekt

import com.djrollins.apigeekt.assignmessage.Request
import com.djrollins.apigeekt.assignmessage.toXml
import java.io.StringWriter
import java.io.Writer
import java.util.*
import java.util.logging.Logger

fun main() {

    val someVariable = Variable("my.variable")
    val writer: Writer = StringWriter()

    assignMessage("Something", Request) {
        assignTo(someVariable)
        assignVariables {
            "var2" ref "some-variable"
            "var2" value "{hardcoded-value}"
            "var3" template "{some-variable}-{some-other-variable}"
        }
        add {
            headers {
                "x-request-id" to UUID.randomUUID().toString()
                "x-request-signature" to "this is crazy yo"
            }
            params {
                "foo" to "bar"
            }
        }
        copy {
            headers {
                "x-request-id" to UUID.randomUUID().toString()
            }
            params {}
            payload = true
        }
    }.toXml(writer)

    Logger.getGlobal().info(writer.toString())
}
