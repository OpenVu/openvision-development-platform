DESCRIPTION = "qcacld-2.0 module.bbclass mechanism."
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://Android.mk;md5=235cc8d87e0fb1c956be4af0d07074fb"
CAF_MIRROR = "https://www.codeaurora.org/cgit/external/wlan"

inherit module

COMPATIBLE_MACHINE = "^(osmio4k|osmio4kplus|osmini4k|spycat4kmini|spycat4k|spycat4kcombo)$"

SRC_URI = "${CAF_MIRROR}/qcacld-2.0/snapshot/qcacld-2.0-${PV}.tar.gz \
    file://qcacld-2.0-support.patch \
"

SRC_URI[md5sum] = "83518ef7b9b73ba9458e95f5a4112840"
SRC_URI[sha256sum] = "13da04a0ce743d3a0678363439a977425e159ef1c83ab12e8caeeffab014c1a0"

SRC_URI_append_spycat4kmini += "file://qcacld-2.0-support-xc7439.patch"
SRC_URI_append_spycat4k += "file://qcacld-2.0-support-xc7439.patch"
SRC_URI_append_spycat4kcombo += "file://qcacld-2.0-support-xc7439.patch"

EXTRA_OEMAKE_append_spycat4kmini += " CONFIG_CLD_HL_SDIO_CORE=y"
EXTRA_OEMAKE_append_spycat4k += " CONFIG_CLD_HL_SDIO_CORE=y"
EXTRA_OEMAKE_append_spycat4kcombo += " CONFIG_CLD_HL_SDIO_CORE=y"

S = "${WORKDIR}/qcacld-2.0-${PV}"

do_install() {
    install -d ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra
    install -m 0644 ${S}/wlan.ko ${D}${nonarch_base_libdir}/modules/${KERNEL_VERSION}/extra
}

python do_package_prepend() {
    d.appendVar('PKGV', '-')
    d.appendVar('PKGV', d.getVar("KERNEL_VERSION", True).split("-")[0])
}
