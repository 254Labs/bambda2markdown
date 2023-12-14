@type: Utility
@author: 254 Labs (https://github.com/254labs/)
@source: GitHub
@createdOn: Nov 16, 2023
@useCase: Filter for requests that do not contain same HTTP Protocol version in responses.

@bambda
String requestProtocol = requestResponse.finalRequest().httpVersion();
String responseProtocol = requestResponse.originalResponse().httpVersion();

if (requestProtocol.contentEquals(responseProtocol.toString())) {
    return false;
}

return true;
