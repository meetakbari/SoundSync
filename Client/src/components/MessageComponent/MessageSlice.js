import { createSlice } from '@reduxjs/toolkit'


const messageSlice = createSlice({
  name: 'messageState',
  initialState:{
    show_message : false,
    message_type : "success",
    message_content: ""
  },
  reducers: {
    showMessage(state, action) {
      state.show_message =  true
      state.message_type = action.payload.message_type
      state.message_content = action.payload.message_content
    },
    hideMessage(state){
        state.show_message = false
    }
  },
})

export const { showMessage, hideMessage } = messageSlice.actions;
export default messageSlice.reducer;