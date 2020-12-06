import { useState } from "react"
import InputField from "../components/InputField"
import { get } from "../grpc/client"
import Spinner from "../components/Spinner"
import Record from "../components/Record"
import Header from "../components/Header"
import "../css/Get.css"
import "../css/Page.css"

function Get(){
    const [recordKey, setRecordKey] = useState("")
    const [fetchingData, setFetchingData] = useState(false)
    const [previous, setPrevious] = useState({})
    const [record, setRecord] = useState(undefined)

    const fetchDoc = () => {
        setFetchingData(true)
        get(recordKey, (err, result) => {
            setFetchingData(false)
            if(err) console.log(err)
            else{
                if(result.getResulttype() === 0) setRecord(result)
                else console.log("error")
            }
        })
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