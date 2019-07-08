# ApigeeKt

A heavily-WIP type-safe DSL to generate Apigee policies and bundles.


## Example policy
```xml
<AssignMessage continueOnError="[false|true]" enabled="[true|false]" name="policy_name" >
  <Add>
    <FormParams> // POST Request only
      <FormParam name="formparam_name">formparam_value</FormParam>
    </FormParams>
    <Headers>
      <Header name="header_name">header_value</Header>
    </Headers>
    <QueryParams> // GET Request only
      <QueryParam name="queryparam_name">queryparam_value</QueryParam>
    </QueryParams>
  </Add>

  <AssignTo createNew="[true|false]" transport="http" type="[request|response]">
     destination_variable_name
  </AssignTo>

  <AssignVariable>
    <Name>variable_name</Name>
    <Ref>source_variable</Ref> // mutually exclusive
    <Template>message_template</Template> // mutually exclusive
    <Value>variable_value</Value> // mutually exclusive
  </AssignVariable>

  <Copy source="[request|response]"> <FormParams>
      <FormParam name="formparam_name">formparam_value</FormParam> // These should be bools? Docs lying?
    </FormParams>
    <Headers>
      <Header name="header_name">header_value</Header>
    </Headers>
    <Path>[false|true]</Path>
    <Payload>[false|true]</Payload>
    <QueryParams>
      <QueryParam name="queryparam_name">queryparam_value</QueryParam>
    </QueryParams>
    <ReasonPhrase>[false|true]</ReasonPhrase>
    <StatusCode>[false|true]</StatusCode>
    <Verb>[false|true]</Verb>
    <Version>[false|true]</Version>
  </Copy>

  <DisplayName>policy_display_name</DisplayName>

  <IgnoreUnresolvedVariables>[true|false]</IgnoreUnresolvedVariables>

  <Remove>
    <FormParams>
      <FormParam name="formparam_name">formparam_value</FormParam>
    </FormParams>
    <Headers>
      <Header name="header_name">header_value</Header>
    </Headers>
    <Payload>[false|true]</Payload>
    <QueryParams>
      <QueryParam name="queryparam_name">queryparam_value</QueryParam>
    </QueryParams>
  </Remove>

  <Set>
    <FormParams>
      <FormParam name="formparam_name">formparam_value</FormParam>
    </FormParams>
    <Headers>
      <Header name="header_name">header_value</Header>
    </Headers>
    <Path>path</Path>
    <Payload contentType="content_type" variablePrefix="prefix"
        variableSuffix="suffix">new_payload</Payload>
    <QueryParams>
      <QueryParam name="queryparam_name">queryparam_value</QueryParam>
    </QueryParams>
    <ReasonPhrase>reason_for_error or {variable}</ReasonPhrase>
    <StatusCode>HTTP_status_code or {variable}</StatusCode>
    <Verb>[GET|POST|PUT|PATCH|DELETE|{variable}]</Verb>
    <Version>[1.0|1.1|{variable}]</Verb>
  </Set>
</AssignMessage>
```
