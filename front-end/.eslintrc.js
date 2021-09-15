module.exports = {
  'env': {
    'browser': true,
    'es6': true,
    'jest': true,
    'node': true,
  },
  'parser': '@typescript-eslint/parser',
  'extends': [
    'eslint:recommended',
    'plugin:react/recommended',
    "plugin:@typescript-eslint/recommended",
  ],
  'globals': {
    'Atomics': 'readonly',
    'SharedArrayBuffer': 'readonly',

  },
  'parserOptions': {
    'ecmaFeatures': {
      'jsx': true,
    },
    'ecmaVersion': 2018,
    'sourceType': 'module',
  },
  'plugins': [
    'react',
    "@typescript-eslint"
  ],
  'settings': {
    'react': {

      'version': 'detect',
    }
  },
  'rules': {
    '@typescript-eslint/no-var-requires': 0,
    "@typescript-eslint/explicit-module-boundary-types": "off",
  },
  "overrides": [
    {
      // enable the rule specifically for TypeScript files
      "files": [
        "*.ts",
        "*.tsx"
      ],
      "rules": {
        "@typescript-eslint/explicit-module-boundary-types": [
          "error"
        ]
      }
    }
  ]
};
