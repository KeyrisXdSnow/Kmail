<#assign session = Session.SPRING_SECURITY_CONTEXT??>
<#if session>
    <#assign
        user=Session.SPRING_SECURITY_CONTEXT.authentication.principal
        name = user.getUsername()
        activeEmailName = user.getActiveEmailName()
        isAdmin = user.isAdmin()
    >
<#else>
    <#assign
        isAdmin = false
        activeEmailName = 'none'
    >
</#if>