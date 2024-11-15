import { Injectable } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';

@Injectable({
  providedIn: 'root',
})
export class SidebarService {
  private sidenav!: MatSidenav;
  private isOpen: boolean = false;

  public setSidenav(sidenav: MatSidenav) {
    this.sidenav = sidenav;
  }

  public toggleSidebar() {
    this.sidenav.toggle().then(() => {
      this.isOpen = this.sidenav.opened;
    });
  }

  public closeSidebar() {
    this.sidenav.close().then(() => {
      this.isOpen = this.sidenav.opened;
    });
  }

  public isSidebarOpen(): boolean {
    return this.isOpen;
  }
}
