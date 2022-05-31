import React, { Component } from 'react'
import SplitPane from 'react-split-pane'
import SideNavBar from './SideNavbarComponent/SideNavBar'
import SampleComponent from './SongStreamingComponent/testsock'
import Chunkplay from './chunkplay'
import SearchSong from './SearchComponent/SearchSong'


export default class Home extends Component {
    render() {
        return (
            <div className="Home">
                <SplitPane split="horizontal" primary="second" minSize={150}>
                    {/* <SideNavBar />
                    <SplitPane split="horizontal " primary="second" minSize={150}> */}
                        <div >
                           <SearchSong />
                        </div>
                        <div >
                            <SampleComponent />
                            {/* <Chunkplay /> */}
                        </div>
                    {/* </SplitPane> */}
                </SplitPane>
            </div>
        )
    }
}
