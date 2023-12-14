type: Utility<br/>
author: 254 Labs (https://github.com/254labs/)<br/>
source: GitHub<br/>
createdOn: Nov 16, 2023<br/>
useCase: Filter for requests that do not contain same HTTP Protocol version in responses.<br/>

```java

String requestProtocol=requestResponse.finalRequest().httpVersion();
String responseProtocol=requestResponse.originalResponse().httpVersion();

if(requestProtocol.contentEquals(responseProtocol.toString())){
return false;
}

return true;

```
