package com.djrollins.apigeekt

import com.djrollins.apigeekt.dsl.assignmessage.assignMessage
import com.djrollins.apigeekt.model.assignmessage.toXml
import java.io.StringWriter
import java.io.Writer
import java.util.*
import java.util.logging.Logger

fun main() {

    val someVariable = Variable("my.variable")
    val writer: Writer = StringWriter()

    // TODO validate no spaces in the name
    assignMessage("ResponseOnly").response {
        add {
            headers {

            }
        }
    }

    assignMessage("RequestOnly").request {
        add {
            headers {}
            queryParams {}
            formParams {}
        }
    }

    assignMessage("GetRequest").request.get {
        add {
            headers {}
            queryParams {}
        }
    }

    assignMessage("PostRequest").request.post {
        add {
            headers {}
            formParams {}
        }
    }

    assignMessage("Something") {
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
            queryParams {
                "queryVariable" to "value"
            }
            formParams {
                "formVariable" to "value"
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
