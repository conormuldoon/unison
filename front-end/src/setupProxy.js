const { createProxyMiddleware } = require('http-proxy-middleware');
const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

const filter = function (pathName, req) {

  const headers = req.headers;
  const accept = headers.accept;

  if (accept === 'application/hal+json' || accept === 'application/geo+jason' ||
    accept === 'application/json') {
    return true;
  }

  var request = new XMLHttpRequest();
  request.open('HEAD', req.hostname + pathName, false);  // The `false` argument makes the request synchronous.
  request.send(null);

  if (request.ok) {
    return false;
  }

  return false;

};

const apiProxy = createProxyMiddleware(filter, {
  target: 'http://localhost:8080',
});

module.exports = function (app) {
  app.use('/', apiProxy);
};
