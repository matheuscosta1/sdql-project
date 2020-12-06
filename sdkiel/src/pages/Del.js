import { useState } from "react"
import InputField from "../components/InputField"
import { IoIosArrowDown, IoIosArrowUp } from "react-icons/io"
import { del, delVersion } from "../grpc/client"
import Spinner from "../components/Spinner"
import Header from "../components/Header"
import "../css/Del.css"
import "../css/Page.css"

function Del(){
    const [showAdvanced, setShowAdvanced] = useState(false)
    const [recordKey, setRecordKey] = useState("")
    const [version, setVersion] = useState(undefined)
    const [deletingData, setDeletingData] = useState(false)

    const deleteData = () => {
        setDeletingData(true)
        if(version){
            delVersion(recordKey, version, handleResult)
        }else del(recordKey, handleResult)
    }

    const handleResult = (err, result) => {
        setDeletingData(false)
        if(err) console.log(err)
        else{
            if(result.getResulttype() === 0){
                console.log("sucesso")
                reset()
            }else console.log("error")
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
                    Advanced {showAdvanced? <IoIosArrowUp /> : <IoIosArrowDown />}
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