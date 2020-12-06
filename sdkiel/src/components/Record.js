import React from "react"
import "../css/Record.css"

function DataField(key, value){
    return (
        <tr className="data-field" key={key}>
            <td className="key">{key}: </td>
            <td className="value">{value}</td>
        </tr>
    )
}

function JSONViewer(json){
    return (
        <table className="json">
            {
                Object.keys(json).map(key => DataField(key, json[key]))
            }
        </table>
    )
}

function Record(props){
    const {record} = props
    const data = Buffer.from(record.getData()).toString()
    const json = data? JSON.parse(data) : undefined

    return (
        <table className="record">
            <tbody>
                <tr>
                    <td>Version</td>
                    <td>{record.getVersion()}</td>
                </tr>
                <tr>
                    <td>Timestamp</td>
                    <td>{record.getTimestamp()}</td>
                </tr>
                <tr>
                    <td>Data</td>
                    <td>
                        { json && JSONViewer(json) }
                    </td>
                </tr>
            </tbody>
        </table>
    )
}

export default Record