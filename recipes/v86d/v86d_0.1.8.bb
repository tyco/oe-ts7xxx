DESCRIPTION = "User support binary for the uvesafb kernel module"
SRC_URI = "http://dev.gentoo.org/~spock/projects/uvesafb/archive/v86d-${PV}.tar.bz2 \
           file://fbsetup"
DEPENDS = "virtual/kernel klibc"
LICENSE = "GPLv2"
PR = "r0"

RRECOMMENDS_${PN} = "kernel-module-uvesafb"

PACKAGE_ARCH = "${MACHINE_ARCH}"
COMPATIBLE_HOST = 'i.86.*-linux'

INITSCRIPT_NAME = "fbsetup"
INITSCRIPT_PARAMS = "start 0 S ."

export CC=${TARGET_PREFIX}klcc

# klcc doesn't work with -isystem
export TARGET_CPPFLAGS = "-I${STAGING_DIR_TARGET}/${layout_includedir}"

do_configure () {
	./configure --default
}

do_compile () {
	KDIR="${STAGING_KERNEL_DIR}" make
}

do_install () {
	install -d ${D}${base_sbindir}
	install v86d ${D}${base_sbindir}/

        install -d ${D}${sysconfdir}/init.d/
        install -m 0755 ${WORKDIR}/fbsetup ${D}${sysconfdir}/init.d/fbsetup
}

inherit update-rc.d
