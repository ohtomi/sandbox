package main

import (
	"fmt"
	"runtime"

	"github.com/cratonica/trayhost"
)

// Refer to documentation at http://github.com/cratonica/trayhost for generating this
// var iconData []byte

func main() {
	// EnterLoop must be called on the OS's main thread
	runtime.LockOSThread()

	go func() {
		// Run your application/server code in here. Most likely you will
		// want to start an HTTP server that the user can hit with a browser
		// by clicking the tray icon.

		// Be sure to call this to link the tray icon to the target url
		trayhost.SetUrl("http://github.com/ohtomi/sandbox/sandobox-trayhost")
	}()

	// Enter the host system's event loop
	trayhost.EnterLoop("Quit this", iconData)

	// This is only reached once the user chooses the Exit menu item
	fmt.Println("Exiting")
}
