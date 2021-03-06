import { useState, useContext } from "react"
import InputField from "../components/InputField"
import InputKeyPair from "../components/InputKeyPair"
import { get, createRecord, bytesToObject, testAndSet } from "../grpc/client"
import Spinner from "../components/Spinner"
import Header from "../components/Header"
import { MessageContext } from "../context/MessageContext"
import { IoIosAdd } from "react-icons/io"
import "../css/Upd.css"
import "../css/Page.css"

function Upd(){
    const { createMessage } = useContext(MessageContext)
    const [record, setRecord] = useState(undefined)
    const [data, setData] = useState([])
    const [recordKey, setRecordKey] = useState("")
    const [savingData, setSavingData] = useState(false)

    const getDocument = () => {
        if(Number.isInteger(parseInt(recordKey))){
            setSavingData(true)
            get(recordKey, (err, result) => {
                setSavingData(false)
                if(err) createMessage("Error", `Something that shouldn't happen has happened! Error code (${err.code})`, "error")
                else{
                    if(result.getResulttype() === 0){
                        const str = bytesToObject(result.getRecord().getData())
                        console.log(result, str)
                        setRecord(result)
                        if(str){
                            const json = JSON.parse(str)
                            setData(Object.keys(json).map(key => ({key, value: json[key]})))
                        }else setData([])
                    }else createMessage("Error", `Wow! Something weird happened. (Error code: ${result.getResulttype()})`, "error")
                }
            })
        }else createMessage("Error", `Wait! I think key must be an integer, right?!`, "error")
    }

    const handleDelete = (index) => {
        data.splice(index, 1)
        setData([...data])
    }

    const addField = () => {
        setData([...data, {key:"", value:""}])
    }

    const getData = () => {
        const obj = {}

        for(let i=0, length=data.length; i<length; i++){
            const {key, value} = data[i]
            if(key) obj[key] = value
        }

        return obj
    }

    const updateDoc = () => {
        setSavingData(true)
        testAndSet(recordKey, record.getRecord().getVersion(), createRecord(undefined, undefined, Buffer.from(JSON.stringify(getData()))), (err, result) => {
            setSavingData(false)
            if(err) createMessage("Error", err)
            else{
                reset()
                createMessage("Success", "The record was updated sucessfully!")
            }
        })
    }

    const reset = () => {
        setRecord(undefined)
        setData([])
        setRecordKey("")
    }

    return (
        <div className="upd">
            <Header withLogo={false} />                
            <div className="dashboard">
                <h1 className="name">upd</h1>
                <button onClick={getDocument} disabled={!recordKey}>get document</button>
                <button onClick={updateDoc} disabled={!record} style={{animationDelay: "0.6s"}}>update document</button>
                {
                    savingData && <Spinner />
                }
            </div>
            <div className="info">
                <div className="info-data">
                    <InputField name="key" placeholder="key" state={[recordKey, setRecordKey]} />
                </div>
                <div className="key-pairs">
                    { record && <> 
                            <div className="key-pairs-top">
                                <h1 className="data-title">Data</h1>
                                <button onClick={addField}><IoIosAdd /> add field</button>
                            </div>
                            <div className="data-fields">
                                {
                                   record && data.map((values, index) => 
                                        <InputKeyPair 
                                            values={values} 
                                            key={index+values.key}
                                            handleDelete={() => handleDelete(index)}
                                        />
                                   )
                                }
                            </div>
                        </>
                    }
                </div>
            </div>
        </div>
    )
}

export default Upd