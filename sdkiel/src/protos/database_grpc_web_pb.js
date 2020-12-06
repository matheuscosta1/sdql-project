/**
 * @fileoverview gRPC-Web generated client stub for sd.nosql.prototype
 * @enhanceable
 * @public
 */

// GENERATED CODE -- DO NOT EDIT!


/* eslint-disable */
// @ts-nocheck



const grpc = {};
grpc.web = require('grpc-web');

const proto = {};
proto.sd = {};
proto.sd.nosql = {};
proto.sd.nosql.prototype = require('./database_pb.js');

/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.sd.nosql.prototype.DatabaseServiceClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options['format'] = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

};


/**
 * @param {string} hostname
 * @param {?Object} credentials
 * @param {?Object} options
 * @constructor
 * @struct
 * @final
 */
proto.sd.nosql.prototype.DatabaseServicePromiseClient =
    function(hostname, credentials, options) {
  if (!options) options = {};
  options['format'] = 'text';

  /**
   * @private @const {!grpc.web.GrpcWebClientBase} The client
   */
  this.client_ = new grpc.web.GrpcWebClientBase(options);

  /**
   * @private @const {string} The hostname
   */
  this.hostname_ = hostname;

};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.sd.nosql.prototype.RecordInput,
 *   !proto.sd.nosql.prototype.RecordResult>}
 */
const methodDescriptor_DatabaseService_set = new grpc.web.MethodDescriptor(
  '/sd.nosql.prototype.DatabaseService/set',
  grpc.web.MethodType.UNARY,
  proto.sd.nosql.prototype.RecordInput,
  proto.sd.nosql.prototype.RecordResult,
  /**
   * @param {!proto.sd.nosql.prototype.RecordInput} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.sd.nosql.prototype.RecordResult.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.sd.nosql.prototype.RecordInput,
 *   !proto.sd.nosql.prototype.RecordResult>}
 */
const methodInfo_DatabaseService_set = new grpc.web.AbstractClientBase.MethodInfo(
  proto.sd.nosql.prototype.RecordResult,
  /**
   * @param {!proto.sd.nosql.prototype.RecordInput} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.sd.nosql.prototype.RecordResult.deserializeBinary
);


/**
 * @param {!proto.sd.nosql.prototype.RecordInput} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.sd.nosql.prototype.RecordResult)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.sd.nosql.prototype.RecordResult>|undefined}
 *     The XHR Node Readable Stream
 */
proto.sd.nosql.prototype.DatabaseServiceClient.prototype.set =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/sd.nosql.prototype.DatabaseService/set',
      request,
      metadata || {},
      methodDescriptor_DatabaseService_set,
      callback);
};


/**
 * @param {!proto.sd.nosql.prototype.RecordInput} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.sd.nosql.prototype.RecordResult>}
 *     Promise that resolves to the response
 */
proto.sd.nosql.prototype.DatabaseServicePromiseClient.prototype.set =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/sd.nosql.prototype.DatabaseService/set',
      request,
      metadata || {},
      methodDescriptor_DatabaseService_set);
};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.sd.nosql.prototype.Key,
 *   !proto.sd.nosql.prototype.RecordResult>}
 */
const methodDescriptor_DatabaseService_get = new grpc.web.MethodDescriptor(
  '/sd.nosql.prototype.DatabaseService/get',
  grpc.web.MethodType.UNARY,
  proto.sd.nosql.prototype.Key,
  proto.sd.nosql.prototype.RecordResult,
  /**
   * @param {!proto.sd.nosql.prototype.Key} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.sd.nosql.prototype.RecordResult.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.sd.nosql.prototype.Key,
 *   !proto.sd.nosql.prototype.RecordResult>}
 */
const methodInfo_DatabaseService_get = new grpc.web.AbstractClientBase.MethodInfo(
  proto.sd.nosql.prototype.RecordResult,
  /**
   * @param {!proto.sd.nosql.prototype.Key} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.sd.nosql.prototype.RecordResult.deserializeBinary
);


/**
 * @param {!proto.sd.nosql.prototype.Key} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.sd.nosql.prototype.RecordResult)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.sd.nosql.prototype.RecordResult>|undefined}
 *     The XHR Node Readable Stream
 */
proto.sd.nosql.prototype.DatabaseServiceClient.prototype.get =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/sd.nosql.prototype.DatabaseService/get',
      request,
      metadata || {},
      methodDescriptor_DatabaseService_get,
      callback);
};


/**
 * @param {!proto.sd.nosql.prototype.Key} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.sd.nosql.prototype.RecordResult>}
 *     Promise that resolves to the response
 */
proto.sd.nosql.prototype.DatabaseServicePromiseClient.prototype.get =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/sd.nosql.prototype.DatabaseService/get',
      request,
      metadata || {},
      methodDescriptor_DatabaseService_get);
};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.sd.nosql.prototype.Key,
 *   !proto.sd.nosql.prototype.RecordResult>}
 */
const methodDescriptor_DatabaseService_del = new grpc.web.MethodDescriptor(
  '/sd.nosql.prototype.DatabaseService/del',
  grpc.web.MethodType.UNARY,
  proto.sd.nosql.prototype.Key,
  proto.sd.nosql.prototype.RecordResult,
  /**
   * @param {!proto.sd.nosql.prototype.Key} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.sd.nosql.prototype.RecordResult.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.sd.nosql.prototype.Key,
 *   !proto.sd.nosql.prototype.RecordResult>}
 */
const methodInfo_DatabaseService_del = new grpc.web.AbstractClientBase.MethodInfo(
  proto.sd.nosql.prototype.RecordResult,
  /**
   * @param {!proto.sd.nosql.prototype.Key} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.sd.nosql.prototype.RecordResult.deserializeBinary
);


/**
 * @param {!proto.sd.nosql.prototype.Key} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.sd.nosql.prototype.RecordResult)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.sd.nosql.prototype.RecordResult>|undefined}
 *     The XHR Node Readable Stream
 */
proto.sd.nosql.prototype.DatabaseServiceClient.prototype.del =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/sd.nosql.prototype.DatabaseService/del',
      request,
      metadata || {},
      methodDescriptor_DatabaseService_del,
      callback);
};


/**
 * @param {!proto.sd.nosql.prototype.Key} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.sd.nosql.prototype.RecordResult>}
 *     Promise that resolves to the response
 */
proto.sd.nosql.prototype.DatabaseServicePromiseClient.prototype.del =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/sd.nosql.prototype.DatabaseService/del',
      request,
      metadata || {},
      methodDescriptor_DatabaseService_del);
};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.sd.nosql.prototype.Version,
 *   !proto.sd.nosql.prototype.RecordResult>}
 */
const methodDescriptor_DatabaseService_delVersion = new grpc.web.MethodDescriptor(
  '/sd.nosql.prototype.DatabaseService/delVersion',
  grpc.web.MethodType.UNARY,
  proto.sd.nosql.prototype.Version,
  proto.sd.nosql.prototype.RecordResult,
  /**
   * @param {!proto.sd.nosql.prototype.Version} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.sd.nosql.prototype.RecordResult.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.sd.nosql.prototype.Version,
 *   !proto.sd.nosql.prototype.RecordResult>}
 */
const methodInfo_DatabaseService_delVersion = new grpc.web.AbstractClientBase.MethodInfo(
  proto.sd.nosql.prototype.RecordResult,
  /**
   * @param {!proto.sd.nosql.prototype.Version} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.sd.nosql.prototype.RecordResult.deserializeBinary
);


/**
 * @param {!proto.sd.nosql.prototype.Version} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.sd.nosql.prototype.RecordResult)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.sd.nosql.prototype.RecordResult>|undefined}
 *     The XHR Node Readable Stream
 */
proto.sd.nosql.prototype.DatabaseServiceClient.prototype.delVersion =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/sd.nosql.prototype.DatabaseService/delVersion',
      request,
      metadata || {},
      methodDescriptor_DatabaseService_delVersion,
      callback);
};


/**
 * @param {!proto.sd.nosql.prototype.Version} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.sd.nosql.prototype.RecordResult>}
 *     Promise that resolves to the response
 */
proto.sd.nosql.prototype.DatabaseServicePromiseClient.prototype.delVersion =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/sd.nosql.prototype.DatabaseService/delVersion',
      request,
      metadata || {},
      methodDescriptor_DatabaseService_delVersion);
};


/**
 * @const
 * @type {!grpc.web.MethodDescriptor<
 *   !proto.sd.nosql.prototype.RecordUpdate,
 *   !proto.sd.nosql.prototype.RecordResult>}
 */
const methodDescriptor_DatabaseService_testAndSet = new grpc.web.MethodDescriptor(
  '/sd.nosql.prototype.DatabaseService/testAndSet',
  grpc.web.MethodType.UNARY,
  proto.sd.nosql.prototype.RecordUpdate,
  proto.sd.nosql.prototype.RecordResult,
  /**
   * @param {!proto.sd.nosql.prototype.RecordUpdate} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.sd.nosql.prototype.RecordResult.deserializeBinary
);


/**
 * @const
 * @type {!grpc.web.AbstractClientBase.MethodInfo<
 *   !proto.sd.nosql.prototype.RecordUpdate,
 *   !proto.sd.nosql.prototype.RecordResult>}
 */
const methodInfo_DatabaseService_testAndSet = new grpc.web.AbstractClientBase.MethodInfo(
  proto.sd.nosql.prototype.RecordResult,
  /**
   * @param {!proto.sd.nosql.prototype.RecordUpdate} request
   * @return {!Uint8Array}
   */
  function(request) {
    return request.serializeBinary();
  },
  proto.sd.nosql.prototype.RecordResult.deserializeBinary
);


/**
 * @param {!proto.sd.nosql.prototype.RecordUpdate} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @param {function(?grpc.web.Error, ?proto.sd.nosql.prototype.RecordResult)}
 *     callback The callback function(error, response)
 * @return {!grpc.web.ClientReadableStream<!proto.sd.nosql.prototype.RecordResult>|undefined}
 *     The XHR Node Readable Stream
 */
proto.sd.nosql.prototype.DatabaseServiceClient.prototype.testAndSet =
    function(request, metadata, callback) {
  return this.client_.rpcCall(this.hostname_ +
      '/sd.nosql.prototype.DatabaseService/testAndSet',
      request,
      metadata || {},
      methodDescriptor_DatabaseService_testAndSet,
      callback);
};


/**
 * @param {!proto.sd.nosql.prototype.RecordUpdate} request The
 *     request proto
 * @param {?Object<string, string>} metadata User defined
 *     call metadata
 * @return {!Promise<!proto.sd.nosql.prototype.RecordResult>}
 *     Promise that resolves to the response
 */
proto.sd.nosql.prototype.DatabaseServicePromiseClient.prototype.testAndSet =
    function(request, metadata) {
  return this.client_.unaryCall(this.hostname_ +
      '/sd.nosql.prototype.DatabaseService/testAndSet',
      request,
      metadata || {},
      methodDescriptor_DatabaseService_testAndSet);
};


module.exports = proto.sd.nosql.prototype;

