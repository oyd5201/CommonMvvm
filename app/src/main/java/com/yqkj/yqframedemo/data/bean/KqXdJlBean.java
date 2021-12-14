package com.yqkj.yqframedemo.data.bean;

public class KqXdJlBean {
    private String id;
    private String dftionTime;
    private String beginPoint;
    private String endPoint;
    private String timeLong;
    private String typeName;
    private String roomName;
    private String img;
    private String workPersonName;
    private String method;
    private String dataType;
    private String number;
    private String healthCertificateImg;
    private boolean isCHoose;
    private HealthCertificateVo healthCertificateVo;

    public HealthCertificateVo getHealthCertificateVo() {
        return healthCertificateVo;
    }

    public void setHealthCertificateVo(HealthCertificateVo healthCertificateVo) {
        this.healthCertificateVo = healthCertificateVo;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getHealthCertificateImg() {
        return healthCertificateImg;
    }

    public void setHealthCertificateImg(String healthCertificateImg) {
        this.healthCertificateImg = healthCertificateImg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isCHoose() {
        return isCHoose;
    }

    public void setCHoose(boolean CHoose) {
        isCHoose = CHoose;
    }

    public String getDftionTime() {
        return dftionTime;
    }

    public void setDftionTime(String dftionTime) {
        this.dftionTime = dftionTime;
    }

    public String getBeginPoint() {
        return beginPoint;
    }

    public void setBeginPoint(String beginPoint) {
        this.beginPoint = beginPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public String getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(String timeLong) {
        this.timeLong = timeLong;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getWorkPersonName() {
        return workPersonName;
    }

    public void setWorkPersonName(String workPersonName) {
        this.workPersonName = workPersonName;
    }
    public class HealthCertificateVo{

        private String id;
        private String name;
        private String healthFrom;
        private String healthNum;
        private String healthTypeName;
        private String organizationId;
        private String userOrganizationId;
        private String idcard;
        private String healthBeginDate;
        private String healthExpireDate;
        private String sex;
        private String age;
        private String standard;
        private String validityTime;
        private String healthBeOverdue;
        private String photo;
        private String healthQrImg;
        private String healthStampImg;
        private String healthStampOrg;
        public void setId(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }

        public void setName(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setHealthFrom(String healthFrom) {
            this.healthFrom = healthFrom;
        }
        public String getHealthFrom() {
            return healthFrom;
        }

        public void setHealthNum(String healthNum) {
            this.healthNum = healthNum;
        }
        public String getHealthNum() {
            return healthNum;
        }

        public void setHealthTypeName(String healthTypeName) {
            this.healthTypeName = healthTypeName;
        }
        public String getHealthTypeName() {
            return healthTypeName;
        }

        public void setOrganizationId(String organizationId) {
            this.organizationId = organizationId;
        }
        public String getOrganizationId() {
            return organizationId;
        }

        public void setUserOrganizationId(String userOrganizationId) {
            this.userOrganizationId = userOrganizationId;
        }
        public String getUserOrganizationId() {
            return userOrganizationId;
        }

        public void setIdcard(String idcard) {
            this.idcard = idcard;
        }
        public String getIdcard() {
            return idcard;
        }

        public void setHealthBeginDate(String healthBeginDate) {
            this.healthBeginDate = healthBeginDate;
        }
        public String getHealthBeginDate() {
            return healthBeginDate;
        }

        public void setHealthExpireDate(String healthExpireDate) {
            this.healthExpireDate = healthExpireDate;
        }
        public String getHealthExpireDate() {
            return healthExpireDate;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
        public String getSex() {
            return sex;
        }

        public void setAge(String age) {
            this.age = age;
        }
        public String getAge() {
            return age;
        }

        public void setStandard(String standard) {
            this.standard = standard;
        }
        public String getStandard() {
            return standard;
        }

        public void setValidityTime(String validityTime) {
            this.validityTime = validityTime;
        }
        public String getValidityTime() {
            return validityTime;
        }

        public void setHealthBeOverdue(String healthBeOverdue) {
            this.healthBeOverdue = healthBeOverdue;
        }
        public String getHealthBeOverdue() {
            return healthBeOverdue;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }
        public String getPhoto() {
            return photo;
        }

        public void setHealthQrImg(String healthQrImg) {
            this.healthQrImg = healthQrImg;
        }
        public String getHealthQrImg() {
            return healthQrImg;
        }

        public void setHealthStampImg(String healthStampImg) {
            this.healthStampImg = healthStampImg;
        }
        public String getHealthStampImg() {
            return healthStampImg;
        }

        public void setHealthStampOrg(String healthStampOrg) {
            this.healthStampOrg = healthStampOrg;
        }
        public String getHealthStampOrg() {
            return healthStampOrg;
        }

    }
}
