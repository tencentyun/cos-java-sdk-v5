package com.qcloud.cos.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Container for image process result.
 */
public class ImageProcessResult {

    /* 返回的原图信息 */
    private OriginalInfo originalInfo;
    /* 每个处理规则对应的处理结果 */
    private List<ImageObject> processResults;


    public OriginalInfo getOriginalInfo() {
        return originalInfo;
    }

    public void setOriginalInfo(OriginalInfo originalInfo) {
        this.originalInfo = originalInfo;
    }

    public ImageProcessResult withOriginalInfo(OriginalInfo originalInfo) {
    	setOriginalInfo(originalInfo);
        return this;
    }

    /**
     * Returns the list of process results.
     */
    public List<ImageObject> getProcessResults() {
        return processResults;
    }

    public boolean addProcessResult(ImageObject object) {
        if (this.processResults == null) {
            this.processResults = new ArrayList<ImageObject> ();
        }
        return this.processResults.add(object);
    }

    /**
     * Sets the results.
     */
    public void setProcessResults(List<ImageObject> results) {
        this.processResults = results;
    }

    public ImageProcessResult withProcessResults(List<ImageObject> results) {
        setProcessResults(results);
        return this;
    }

    /**
     * Convenience array style method for {@link ImageProcessResult#withProcessResults(List)}
     */
    public ImageProcessResult withProcessResults(ImageObject... result) {
        setProcessResults(Arrays.asList(result));
        return this;
    }

    public ImageProcessResult() {
        super();
        this.originalInfo = new OriginalInfo();
    }

    public static class OriginalInfo {

        /* 图片的文件名字 */
        private String key;
        /* 图片的存储路径 */
        private String location;
        /* 图片的详细信息 */
        private ImageInfo imageInfo;

        public OriginalInfo() {
            super();
            this.imageInfo = new ImageInfo();
        }

        public String getKey() {
            return this.key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public OriginalInfo withKey(String key) {
            setKey(key);
            return this;
        }

        public String getLocation() {
            return this.location;
        }
        public void setLocation(String location) {
            this.location = location;
        }
        public OriginalInfo withLocation(String location) {
            setLocation(location);
            return this;
        }

        public ImageInfo getImageInfo() {
            return this.imageInfo;
        }
        public void setImageInfo(ImageInfo imageInfo) {
            this.imageInfo = imageInfo;
        }
        public OriginalInfo withImageInfo(ImageInfo imageInfo) {
            setImageInfo(imageInfo);
            return this;
        }

        public static class ImageInfo {

            /* 图片格式 */
            private String format;
            /* 图片主色调 */
            private String ave;
            /* 图片宽 */
            private int width;
            /* 图片高 */
            private int height;
            /* 图片质量 */
            private int quality;
            /* 图片旋转角度 */
            private int orientation;


            public String getAve() {
                return this.ave;
            }
            public void setAve(String ave) {
                this.ave = ave;
            }
            public ImageInfo withAve(String ave) {
                setAve(ave);
                return this;
            }

            public String getFormat() {
                return this.format;
            }
            public void setFormat(String format) {
                this.format = format;
            }
            public ImageInfo withFormat(String format) {
                setFormat(format);
                return this;
            }

            public int getWidth() {
                return this.width;
            }
            public void setWidth(int width) {
                this.width = width;
            }
            public ImageInfo withWidth(int width) {
                setWidth(width);
                return this;
            }

            public int getHeight() {
                return this.height;
            }
            public void setHeight(int height) {
                this.height = height;
            }
            public ImageInfo withHeight(int height) {
                setHeight(height);
                return this;
            }

            public int getQuality() {
                return this.quality;
            }
            public void setQuality(int quality) {
                this.quality = quality;
            }
            public ImageInfo withQuality(int quality) {
                setQuality(quality);
                return this;
            }

            public int getOrientation() {
                return this.orientation;
            }
            public void setOrientation(int orientation) {
                this.orientation = orientation;
            }
            public ImageInfo withOrientation(int orientation) {
                setOrientation(orientation);
                return this;
            }
        }

    }

    /**
     * The transition attribute of the rule describing how this object will move between different
     * storage classes in Qcloud COS.
     */
    public static class ImageObject {

        /* 图片的文件名字 */
        private String key;
        /* 图片的存储路径 */
        private String location;
        /* 图片格式 */
        private String format;
        /* 图片宽 */
        private int width;
        /* 图片高 */
        private int height;
        /* 图片质量 */
        private int quality;
        /* 图片大小 */
        private long size;


        public String getKey() {
            return this.key;
        }
        public void setKey(String key) {
            this.key = key;
        }
        public ImageObject withKey(String key) {
            setKey(key);
            return this;
        }

        public String getLocation() {
            return this.location;
        }
        public void setLocation(String location) {
            this.location = location;
        }
        public ImageObject withLocation(String location) {
            setLocation(location);
            return this;
        }

        public String getFormat() {
            return this.format;
        }
        public void setFormat(String format) {
            this.format = format;
        }
        public ImageObject withFormat(String format) {
            setFormat(format);
            return this;
        }

        public int getWidth() {
            return this.width;
        }
        public void setWidth(int width) {
            this.width = width;
        }
        public ImageObject withWidth(int width) {
            setWidth(width);
            return this;
        }

        public int getHeight() {
            return this.height;
        }
        public void setHeight(int height) {
            this.height = height;
        }
        public ImageObject withHeight(int height) {
            setHeight(height);
            return this;
        }

        public int getQuality() {
            return this.quality;
        }
        public void setQuality(int quality) {
            this.quality = quality;
        }
        public ImageObject withQuality(int quality) {
            setQuality(quality);
            return this;
        }

        public long getSize() {
            return this.size;
        }
        public void setSize(long size) {
            this.size = size;
        }
        public ImageObject withSize(long size) {
            setSize(size);
            return this;
        }
    }

}
