package com.djrollins.apigeekt

import com.djrollins.apigeekt.common.lit
import com.djrollins.apigeekt.common.ref
import com.djrollins.apigeekt.dsl.assignmessage.assignMessage
import com.djrollins.apigeekt.model.Variable
import com.djrollins.apigeekt.xml.XmlWriter
import com.djrollins.apigeekt.xml.toXml
import java.io.StringWriter
import java.util.*
import java.util.logging.Logger

fun main() {

    val someVariable = Variable("my.variable")
    val writer = XmlWriter(StringWriter())

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
        val variable = ref<Int>("some.variable")

        assignTo {
            name = "request"
            createNew = true
        }

        assignVariables {
            "literal" to "some literal value"
            "ref" to variable
            "template1" to variable + "-" + ref<Float>("some-other-variable")
            "template2" to lit("variable: ") + variable
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

    Logger.getGlobal().info(writer.writer.toString())
}
