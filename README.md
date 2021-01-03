# java执行命令行工具
> 该项目是为了方便在java项目中执行ffmpeg命令行而产生
- [GitHub](https://github.com/sweeter-box/support-cmd.git)
- [Gitee](https://gitee.com/tianmengjun/support-cmd.git)
## 关键词
- java
- ffmpeg
- shell脚本
## 使用示例

### 示例1
- 执行ping命令
```
    public static void main(String[] args) {

        Command command = new Command.Builder()
                .add(CmdConstant.startCommand())
                .add("ping", "qq.com").build();
        CommandExecutor commandExecutor = new CommandLineExecutorImpl();
        commandExecutor.execute(command);
        System.exit(0);
    }

```
- 输出如下：
```
cmd.exe /c ping qq.com
19:52:59.340 [main] INFO cn.lwiki.support.cmd.command.listener.DefaultWinCommandListener - 执行cmd返回信息:
正在 Ping qq.com [61.129.7.47] 具有 32 字节的数据:
来自 61.129.7.47 的回复: 字节=32 时间=63ms TTL=53
来自 61.129.7.47 的回复: 字节=32 时间=39ms TTL=53
来自 61.129.7.47 的回复: 字节=32 时间=39ms TTL=53
来自 61.129.7.47 的回复: 字节=32 时间=39ms TTL=53

61.129.7.47 的 Ping 统计信息:
    数据包: 已发送 = 4，已接收 = 4，丢失 = 0 (0% 丢失)，
往返行程的估计时间(以毫秒为单位):
    最短 = 39ms，最长 = 63ms，平均 = 45ms
```

### 示例2
- 执行ffmpeg命令获取视频文件信息

```
    public static void main(String[] args) {
        //ffprobe -v quiet -print_format json -show_error -show_format -show_streams D:/Desktop/1.mp4
        Command command = new Command.Builder()
                .add(CmdConstant.startCommand())
                .add("ffprobe")
                .add("-v")
                .add("quiet")
                .add("-print_format","json")
                .add("-show_error")
                .add("-show_format")
                .add("-show_streams")
                .add("D:/Desktop/1.mp4")
                .build();
        CommandExecutor commandExecutor = new CommandLineExecutorImpl();
       // commandExecutor.execute(command);
        commandExecutor.execute(command,listenerParam -> {
            try (InputStreamReader inputStreamReader = new InputStreamReader(listenerParam.getProcessInputStream(), "GBK");
                 BufferedReader reader = new BufferedReader(inputStreamReader)) {
                List<String> resultList = reader.lines().collect(Collectors.toList());
                System.out.println(String.format("执行cmd返回信息:\n%s ",String.join(System.getProperty("line.separator"), resultList)));
            }catch(Exception e){
                throw e;
            }
        });

        System.exit(0);
    }
```

```
cmd.exe /c ffprobe -v quiet -print_format json -show_error -show_format -show_streams D:/Desktop/1.mp4
执行cmd返回信息:
{
    "streams": [
        {
            "index": 0,
            "codec_name": "h264",
            "codec_long_name": "H.264 / AVC / MPEG-4 AVC / MPEG-4 part 10",
            "profile": "High",
            "codec_type": "video",
            "codec_time_base": "1/60",
            "codec_tag_string": "avc1",
            "codec_tag": "0x31637661",
            "width": 720,
            "height": 1280,
            "coded_width": 720,
            "coded_height": 1280,
            "closed_captions": 0,
            "has_b_frames": 2,
            "sample_aspect_ratio": "1:1",
            "display_aspect_ratio": "9:16",
            "pix_fmt": "yuv420p",
            "level": 31,
            "color_range": "tv",
            "color_space": "smpte170m",
            "color_transfer": "bt709",
            "color_primaries": "smpte170m",
            "chroma_location": "left",
            "refs": 1,
            "is_avc": "true",
            "nal_length_size": "4",
            "r_frame_rate": "30/1",
            "avg_frame_rate": "30/1",
            "time_base": "1/15360",
            "start_pts": 0,
            "start_time": "0.000000",
            "duration_ts": 1013248,
            "duration": "65.966667",
            "bit_rate": "1300042",
            "bits_per_raw_sample": "8",
            "nb_frames": "1979",
            "disposition": {
                "default": 1,
                "dub": 0,
                "original": 0,
                "comment": 0,
                "lyrics": 0,
                "karaoke": 0,
                "forced": 0,
                "hearing_impaired": 0,
                "visual_impaired": 0,
                "clean_effects": 0,
                "attached_pic": 0,
                "timed_thumbnails": 0
            },
            "tags": {
                "language": "und",
                "handler_name": "VideoHandler"
            }
        },
        {
            "index": 1,
            "codec_name": "aac",
            "codec_long_name": "AAC (Advanced Audio Coding)",
            "profile": "LC",
            "codec_type": "audio",
            "codec_time_base": "1/44100",
            "codec_tag_string": "mp4a",
            "codec_tag": "0x6134706d",
            "sample_fmt": "fltp",
            "sample_rate": "44100",
            "channels": 2,
            "channel_layout": "stereo",
            "bits_per_sample": 0,
            "r_frame_rate": "0/0",
            "avg_frame_rate": "0/0",
            "time_base": "1/44100",
            "start_pts": 0,
            "start_time": "0.000000",
            "duration_ts": 2909101,
            "duration": "65.966009",
            "bit_rate": "128002",
            "max_bit_rate": "128002",
            "nb_frames": "2843",
            "disposition": {
                "default": 1,
                "dub": 0,
                "original": 0,
                "comment": 0,
                "lyrics": 0,
                "karaoke": 0,
                "forced": 0,
                "hearing_impaired": 0,
                "visual_impaired": 0,
                "clean_effects": 0,
                "attached_pic": 0,
                "timed_thumbnails": 0
            },
            "tags": {
                "language": "und",
                "handler_name": "SoundHandler"
            }
        }
    ],
    "format": {
        "filename": "D:/Desktop/1.mp4",
        "nb_streams": 2,
        "nb_programs": 0,
        "format_name": "mov,mp4,m4a,3gp,3g2,mj2",
        "format_long_name": "QuickTime / MOV",
        "start_time": "0.000000",
        "duration": "66.013000",
        "size": "11848895",
        "bit_rate": "1435946",
        "probe_score": 100,
        "tags": {
            "major_brand": "isom",
            "minor_version": "512",
            "compatible_brands": "isomiso2avc1mp41",
            "comment": "vid:v0300f440000bu6qpfoeit1ffdjb2drg",
            "encoder": "Lavf58.20.100"
        }
    }
} 
```
