const path = require('path');
const HtmlWebpackPlugin = require('html-webpack-plugin');

module.exports = {
  devtool: 'cheap-eval-source-map',

  entry: path.resolve(__dirname, 'app/app.module.js'),

  output: {
    path: path.resolve(__dirname, 'build'),
    filename: 'app.bundle.js'
  },

  plugins: [
    new HtmlWebpackPlugin({
      title: 'Meus Filmes',
      filename: 'index.html',
      template: path.resolve(__dirname, 'app/assets/index-template.ejs')
    })
  ],

  module: {
    loaders: [{
      test: /\.css$/, 
      loader: 'style-loader!css-loader'
    }, {
      test: /\.js$/,
      exclude: /node_modules/,
      loader: 'babel-loader'
		}, {
      test: /\.html$/,
      loader: 'raw-loader' 
    }]
	}
};