import "../css/InputField.css"

function InputField(props){
    const { name, placeholder, state } = props
    const [value, setValue] = state

    return (
            <input 
                className="input-field" 
                type="text" 
                name={name} 
                placeholder={placeholder}
                value = {value}
                onChange={(e) => {setValue(e.target.value)}}
            />
    )
}

export default InputField