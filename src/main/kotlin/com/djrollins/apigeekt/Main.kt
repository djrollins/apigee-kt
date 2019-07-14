package com.djrollins.apigeekt

import com.djrollins.apigeekt.common.lit
import com.djrollins.apigeekt.common.ref
import com.djrollins.apigeekt.dsl.policy.assignmessage.assignMessage
import com.djrollins.apigeekt.dsl.proxyEndpoint
import com.djrollins.apigeekt.model.Property
import com.djrollins.apigeekt.model.proxy.RouteRuleTarget.TargetEndpoint
import com.djrollins.apigeekt.model.proxy.VirtualHost
import com.djrollins.apigeekt.xml.XmlWriter
import java.io.StringWriter
import java.nio.file.Path
import java.util.*
import java.util.logging.Logger

fun main() {

    val writer = XmlWriter(StringWriter())

    proxyEndpoint {
        name = "default"

        connection {
            basePath = Path.of("/foo/bar/baz")
            virtualHost = VirtualHost("digital-test.ybs.co.uk")
        }

        routeRule {
            name = "default"
            target = TargetEndpoint("The endpoint")
        }

        preFlow {
            request {
                step {
                    condition = (Property<String>("foo.bar.baz") eq "some string value") and (Property<Int>("foo") ne 200)
                    policy = assignMessage("Something") {
                        val variable = ref<Int>("some.property")

                        assignTo {
                            name = "request"
                            createNew = true
                        }

                        assignVariables {
                            "literal" to "some literal value"
                            "ref" to variable
                            "template1" to variable + "-" + ref<Float>("some-other-property")
                            "template2" to lit("property: ") + variable
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
                    }
                }
            }
        }
    }

    Logger.getGlobal().info(writer.writer.toString())

}
