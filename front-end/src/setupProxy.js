const { createProxyMiddleware } = require('http-proxy-middleware');

const filter = function (pathName, req) {

  const headers = req.headers;
  const accept = headers.accept;

  if (accept === 'application/hal+json' || accept === 'application/geo+jason' ||
    accept === 'application/json' || pathName.startsWith("/locationCollection")) {
    return true;
  }

  return false;



};

const apiProxy = createProxyMiddleware(filter, {
  target: 'http://localhost:8080',
});

module.exports = function (app) {
  app.use('/', apiProxy);
};
