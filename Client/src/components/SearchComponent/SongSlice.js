import { createSlice } from '@reduxjs/toolkit'


const songSlice = createSlice({
  name: 'songState',
  initialState:{
    song_id : null,
  },
  reducers: {
    changeSong(state, action) {
      state.song_id = action.payload
    },
  },
})

export const { changeSong } = songSlice.actions;
export default songSlice.reducer;