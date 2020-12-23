import { useState, useContext } from "react"
import InputField from "../components/InputField"
import { IoIosArrowDown } from "react-icons/io"
import { del, delVersion } from "../grpc/client"
import Spinner from "../components/Spinner"
import Header from "../components/Header"
import { MessageContext } from "../context/MessageContext"
import "../css/Del.css"
import "../css/Page.css"

function Del(){
    const { createMessage } = useContext(MessageContext)
    const [showAdvanced, setShowAdvanced] = useState(false)
    const [recordKey, setRecordKey] = useState("")
    const [version, setVersion] = useState(undefined)
    const [deletingData, setDeletingData] = useState(false)

    const deleteData = () => {
        if(Number.isInteger(parseInt(recordKey))){
            setDeletingData(true)
            if(version){
                delVersion(recordKey, version, handleResult)
            }else del(recordKey, handleResult)
        }else createMessage("Error", `Wait! I think key must be an integer, right?!`, "error")
    }

    const handleResult = (err, result) => {
        setDeletingData(false)
        if(err) createMessage("Error", `Something that shouldn't happen has happened! Error code (${err.code})`, "error")
        else{
            if(result.getResulttype() === 0){
                createMessage("Success", "The record was deleted successfully!")
                reset()
            }else createMessage("Error", `Wow! Something weird happened. (Error code: ${result.getResulttype()})`, "error")
        }
    }

    const reset = () => {
        setRecordKey("")
        setVersion("")
    }

    return (
        <div className="del">
            <Header withLogo={false} /> 
            <div className="dashboard">
                <h1 className="name">del</h1>
                <button onClick={deleteData}>remove document</button>
                {
                    deletingData && <Spinner />
                }
            </div>
            <div className="info">
                <div className="info-data">
                    <InputField name="key" placeholder="key" state={[recordKey, setRecordKey]} />
                </div>
                <div className="advanced">
                <h1
                    className="advanced-title"
                    onClick={() => {setShowAdvanced(!showAdvanced)}}
                >
                    Advanced <span className={`advanced-icon ${showAdvanced?"up":"down"}`}><IoIosArrowDown /></span>
                </h1>
                    {
                        showAdvanced && <div className="advanced-fields">
                            <InputField name="version" placeholder="version" state={[version, setVersion]} />
                        </div>
                    }
                </div>
            </div>
        </div>
    )
}

export default Del