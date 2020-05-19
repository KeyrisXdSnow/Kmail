<#import "messageListItem.ftl" as mlist>

<#macro messageListForm>
<div class="list-group">
    <#if messageList??>
    <#list messageList as message>
        <p>ad</p>
    </#list>
        <#else> not exist
    </#if>
</div>
</#macro>