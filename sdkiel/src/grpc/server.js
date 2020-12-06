const grpc = require("grpc")
const protoLoader = require("@grpc/proto-loader")
const packageDef = protoLoader.loadSync("../protos/database.proto", {})
const grpcObj = grpc.loadPackageDefinition(packageDef)
const databasePackage = grpcObj.sd.nosql.prototype

const server = new grpc.Server()
const db = {}

server.addService(databasePackage.DatabaseService.service, {
    set: (call, callback) => {
        console.log("set")
        // console.log(call.request)
        // console.log(call.request.key.toNumber(), call.request.record.timestamp.toNumber(), call.request.record.data.toString())
        const {key, record} = call.request
        db[key] = record
        console.log(record)
        callback(null, call.request)
    },
    get: (call, callback) => {
        console.log("get")
        const record = db[call.request.key]
        const resultType = record? 0 : 1
        callback(null, {resultType, record})
    },
    del: (call, callback) => {
        console.log("del")
        const record = db[call.request.key]
        const resultType = record? 0 : 1
        callback(null, {resultType, record})
    },
    delVersion: (call, callback) => {
        console.log("delVersion")
        const record = db[call.request.key]
        const resultType = record? 0 : 1
        callback(null, {resultType, record})
    },
    testAndSet: (call,callback) => {
        console.log("test")
        const record = db[call.request.key]
        const resultType = record? 0 : 1
        callback(null, {resultType, record})
    }
})

server.bind("localhost:8080", grpc.ServerCredentials.createInsecure())
server.start()