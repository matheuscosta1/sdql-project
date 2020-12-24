import { useState } from "react"

function useTimer(ms, handleFinished){
    const [state, setState] = useState({
        timer: setTimeout(handleFinished, ms),
        startedAt: new Date().getTime(),
        remaining: ms
    })

    const pauseTimer = () => {
        clearTimeout(state.timer)
        setState({...state, remaining: new Date().getTime() - state.remaining})
    }

    const resumeTimer = () => {
        setState({
            ...state,
            startedAt: new Date().getTime(),
            timer: setTimeout(handleFinished, state.remaining)
        })
    }

    return {pauseTimer, resumeTimer}
}

export default useTimer