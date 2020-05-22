<#import "parts/common.ftl" as common>
<#import "parts/messageList.ftl" as mesList>

<@common.page>
    <#if messageList??>
    <@mesList.messageListForm messageList></@mesList.messageListForm>
        <#else> Либо null либо включи gmail =)
    </#if>
</@common.page>