import { Component, ViewChild, AfterViewInit } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { SidebarService } from '../../services/sidebar.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements AfterViewInit {
  title: string = "MDD"

  @ViewChild('sidenav') sidenav!: MatSidenav;

  constructor(private sidebarService: SidebarService) {}

  ngAfterViewInit():void {
    this.sidebarService.setSidenav(this.sidenav);
  }

  public closeSidebar():void {
    this.sidebarService.closeSidebar();
  }

  get isOpen() {
    return this.sidebarService.isSidebarOpen();
  }
}
