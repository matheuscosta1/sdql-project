import { useState, useContext } from "react"
import InputField from "../components/InputField"
import { get } from "../grpc/client"
import Spinner from "../components/Spinner"
import Record from "../components/Record"
import Header from "../components/Header"
import { MessageContext } from "../context/MessageContext"
import "../css/Get.css"
import "../css/Page.css"

function Get(){
    const { createMessage } = useContext(MessageContext)
    const [recordKey, setRecordKey] = useState("")
    const [fetchingData, setFetchingData] = useState(false)
    const [previous, setPrevious] = useState({})
    const [record, setRecord] = useState(undefined)

    const fetchDoc = () => {
        if(Number.isInteger(parseInt(recordKey))){
            setFetchingData(true)
            get(recordKey, (err, result) => {
                setFetchingData(false)
                if(err) createMessage("Error", `Something that shouldn't happen has happened! Error code (${err.code})`, "error")
                else{
                    if(result.getResulttype() === 0){
                        setRecord(result)
                    }else createMessage("Error", `Wow! Something weird happened. (Error code: ${result.getResulttype()})`, "error")
                }
            })
        }else createMessage("Error", `Wait! I think key must be an integer, right?!`, "error")
    }

    return(
        <div className="get">
            <Header withLogo={false} /> 
            <div className="dashboard">
                <h1 className="name">get</h1>
                <button className="get-btn" disabled={!recordKey} onClick={fetchDoc}>get document</button>
                <div className="previous-docs">
                    { Object.keys(previous).map(key => 
                        <div key={key}>{key}: {previous[key]}</div>
                    ) }
                </div>
                {
                    fetchingData && <Spinner />
                }
            </div>
            <div className="info">
                <div className="info-data">
                    <InputField name="key" placeholder="key" state={[recordKey, setRecordKey]} />
                </div>
                <div className="key-pairs">
                    <div className="data-fields">
                        {
                            record && <Record record={record.getRecord()}/>
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Get