SUMMARY = "Yocto Linux System Image for Avnet RzBoard"
LICENSE = "MIT"

inherit core-image

# Environment setup, support building kernel modules with kernel src in SDK
export KERNELSRC="$SDKTARGETSYSROOT/usr/src/kernel"
export KERNELDIR="$SDKTARGETSYSROOT/usr/src/kernel"
export HOST_EXTRACFLAGS="-I${OECORE_NATIVE_SYSROOT}/usr/include/ -L${OECORE_NATIVE_SYSROOT}/usr/lib"

TOOLCHAIN_TARGET_TASK += " libusb1-dev alsa-dev"

# Basic packages
IMAGE_INSTALL_append = " \
    bash \
    bonnie++ \
    v4l-utils \
    i2c-tools \
    coreutils \
    util-linux \
    busybox \
    libusb1 \
    pciutils \
    iproute2 \
    can-utils \
    ethtool \
    iperf3 \
    openssh \
    openssh-sshd \
    usbutils \
    mtd-utils \
    dosfstools \
    e2fsprogs-badblocks \
    e2fsprogs-dumpe2fs \
    e2fsprogs-e2fsck \
    e2fsprogs-e2scrub \
    e2fsprogs-mke2fs \
    e2fsprogs-resize2fs \
    e2fsprogs-tune2fs \
    minicom \
    memtester \
    alsa-utils \
    libdrm \
    libdrm-tests \
    yavta \
    kernel-module-uvcvideo \
	glib-2.0 \
    gnupg \
    wpa-supplicant \
    hostapd \
    evtest \
    sudo \
    spitools \
    xz \
    udev-extraconf \
	wireless-tools \
	bluez5 \
	rzboard-firmware \
	wlan-conf \
"

IMAGE_INSTALL_append = " \
	packagegroup-gstreamer1.0-plugins \
	packagegroup-wayland-community \
"

IMAGE_FEATURES_remove = " ssh-server-dropbear"
IMAGE_FEATURES_append = " ssh-server-openssh"

# Renesas Basic packages for 32bit
BASIC_32BIT_PKGS = " \
    lib32-coreutils \
    lib32-libstdc++ \
"

# Installation for 32bit packages
IMAGE_INSTALL_append = " \
    ${@oe.utils.conditional("USE_32BIT_PKGS", "1", "${BASIC_32BIT_PKGS}", "", d)} \
"

inherit extrausers
EXTRA_USERS_PARAMS = "\
	usermod -P avnet root; \
"
# additional free disk space created in Kbytes
IMAGE_ROOTFS_EXTRA_SPACE = "512000"
