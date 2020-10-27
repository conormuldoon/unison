const { createProxyMiddleware } = require('http-proxy-middleware');
const XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
const HttpStatus = require('http-status-codes');

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

  if (request.status === HttpStatus.NOT_FOUND) {
    return true;
  }

  return false;

};

const apiProxy = createProxyMiddleware(filter, {
  target: 'https://aqua.ucd.ie/unison',
});

module.exports = function (app) {
  app.use('/', apiProxy);
};
