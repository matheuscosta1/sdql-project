import { useState } from "react"
import InputField from "../components/InputField"
import InputKeyPair from "../components/InputKeyPair"
import { set, createRecord } from "../grpc/client"
import Spinner from "../components/Spinner"
import Header from "../components/Header"
import "../css/Add.css"
import "../css/Page.css"

function Add(){
    const [fields, setFields] = useState({})
    const [fieldsData, setFieldsData] = useState({})
    const [recordKey, setRecordKey] = useState("")
    const [savingData, setSavingData] = useState(false)

    const handleNewField = () => {
        const key = new Date().getTime()
        const obj = {}
        const Component = <InputKeyPair inputs={fieldsData} key={key} handleDelete={() => handleInputDeletion(key)} values={obj}/>
        setFieldsData({...fieldsData, [key]: obj})
        setFields({...fields, [key]: Component})
    }

    const handleInputDeletion = (key) => {
        const {[key]: _, ...others} = fields
        setFields(others)
    }

    const getData = () => {
        let data = {}
        let keys = Object.keys(fields)

        for(let i=0, length=keys.length; i<length; i++){
            let obj = fieldsData[keys[i]]
            if(obj.key) data[obj.key] = obj.value 
        }

        return data
    }

    const addToDatabase = () => {
        if(recordKey){
            const record = createRecord(undefined, undefined, Buffer.from(JSON.stringify(getData())))
            setSavingData(true)
            set(recordKey, record, (err, result) => {
                reset()
                setSavingData(false)
                if(err) console.log(err)
                else console.log(result)
            })
        }
    }

    const reset = () => {
        setRecordKey("")
        setFields({})
        setFieldsData({})
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
                            Object.keys(fields).map(key => fields[key])
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Add