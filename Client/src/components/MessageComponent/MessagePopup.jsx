import React from 'react'
import { useSelector, useDispatch } from 'react-redux'
import {hideMessage} from "./MessageSlice"
import "./message.css"

export default function MessagePopup() {
    const isMessageVsible = useSelector((state)=>(state.MessagePopup.show_message) )
    const MessageType = useSelector((state)=>(state.MessagePopup.message_type) )
    const MessageContent = useSelector((state)=>(state.MessagePopup.message_content) )
    const dispatch = useDispatch()

    const message_type = {
        info:"alert-info",
        warning: "alert-warning",
        success:"alert-success",
        error:"alert-danger"
    }


    if(!isMessageVsible)
    return <></>
    
    return (
        <div>
            <div class={"myalert alert alert-dismissible fade show "+message_type[MessageType]} role="alert">
               {MessageContent}
                <button type="button" className="close" data-dismiss="alert" aria-label="Close" onClick={(e)=>{dispatch(hideMessage())}}>
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
        </div>
    )
}
