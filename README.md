# SoundSync

## Project Overview
What if you can stream songs of your choice from anywhere across the globe just at your fingertips? 
+ In this project of cloud computing, our main focus was to work with virtualization technology to learn how virtual machines work and deploy the developed services across number of VMs to ensure the high reliability while songs are streaming.

## Project Functionality
+ User can upload song file (.mp3 or .wav) & listen it after uploading.
+ Store uploaded file in HDFS through HDFS-Write-API
+ Read song file(to listen for client) from HDFS through HDFS-Read-API
+ Store/Read song metadata to/from MongoDB instances of all/single shard(s).
+ Minimal GUI to upload a song, search the song and listen the song using play/pause button.
+ Deployed server on XCP-ng's VM coordinates between client and HDFS and Metadata DB.

## Project Setup
Complete project was setup on 4 different desktops(having different IP addresses) which were connected through LAN as follows:
- Desktop 1: SoundSync Client
- Desktop 2: Hadoop Distributed File System
- Desktop 3: Song-Metadata Database (3 LXC containers with each having replicated MongoDB instance)
- Desktop 4: SoundSync Server (hosted on XCP-ng VM)

## System Architecture
![Architecture Diagram](https://github.com/meetakbari/SoundSync/blob/main/SoundSync_architecture.png)

## Tech-Stack
+ Java 11
+ React.js
+ MongoDB
+ HDFS
+ LXC (Linux Containers)
+ XCP-ng (Bare-metal Hypervisor)

## Implemented Services' Pseudocodes
+ Pseudocodes of essential services of this project are available inside the SoundSync/Pseudocode-of-Services directory.

## Future Work
+ Improvise streaming service
+ Use more than one storage server (HDFS nodes)
+ Expand the distributed database infrastructure 
+ Add more user centric services
+ Add Auto Scaling and Load Balancing mechanism

### How To Run This Project
+ To run this project, you will require 4 different desktops and few things to setup before running this project such as installing a XCP-ng hypervisor on single pc, installing hadoop with HDFS on another pc and creating LXC containers(shards) with mongodb instance replicated across all the shards.
## Contributors
[![](https://avatars1.githubusercontent.com/u/56075605?s=50&&v=4)](https://github.com/meetakbari)
[![](https://avatars0.githubusercontent.com/u/55320599?s=50&v=4)](https://github.com/MayankkumarTank)
[![](https://avatars.githubusercontent.com/u/49686817?s=50&v=4)](https://github.com/mrchocha) 
[![](https://avatars.githubusercontent.com/u/50065408?s=50&v=4)](https://github.com/JeetKaria06)
