import { useState, useContext } from "react"
import InputField from "../components/InputField"
import InputKeyPair from "../components/InputKeyPair"
import { set, createRecord } from "../grpc/client"
import Spinner from "../components/Spinner"
import Header from "../components/Header"
import { MessageContext } from "../context/MessageContext"

import "../css/Add.css"
import "../css/Page.css"

function Add(){
    const { createMessage } = useContext(MessageContext) 
    const [fields, setFields] = useState([])
    const [recordKey, setRecordKey] = useState("")
    const [savingData, setSavingData] = useState(false)

    const handleNewField = () => setFields([...fields, {key:"", value:""}])

    const handleInputDeletion = (index) => {
        fields.splice(index, 1)
        setFields([...fields])
    }

    const getData = () => {
        const obj = {}

        for(let i=0, length=fields.length; i<length; i++){
            const {key, value} = fields[i]
            if(key) obj[key] = value
        }

        return obj
    }

    const addToDatabase = () => {
        if(recordKey && Number.isInteger(parseInt(recordKey))){
            const record = createRecord(undefined, undefined, Buffer.from(JSON.stringify(getData())))
            setSavingData(true)
            set(recordKey, record, (err, result) => {
                reset()
                setSavingData(false)
                if(err){
                    console.log(err)
                    createMessage("Error", `Something that shouldn't happen has happened! Error code (${err.code})`, "error")
                }else createMessage("Success", "The record was inserted successfully!")
            })
        }else createMessage("Error", `Wait! I think key must be an integer, right?!`, "error")
    }

    const reset = () => {
        setRecordKey("")
        setFields([])
    }

    return (
        <div className="add">
            <Header withLogo={false} />                
            <div className="dashboard">
                <h1 className="name">add</h1>
                <button onClick={handleNewField}>add field</button>
                <button onClick={addToDatabase} disabled={!recordKey} style={{animationDelay: "0.6s"}}>add to database</button>
                {
                    savingData && <Spinner />
                }
            </div>
            <div className="info">
                <div className="info-data">
                    <InputField name="key" placeholder="key" state={[recordKey, setRecordKey]} />
                </div>
                <div className="key-pairs">
                    <h1 className="data-title">Data</h1>
                    <div className="data-fields">
                        {
                            fields.map((obj, index) => (
                                <InputKeyPair 
                                    values={obj} 
                                    key={obj.key+index}
                                    handleDelete = {() => {handleInputDeletion(index)}}
                                />
                            ))
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Add