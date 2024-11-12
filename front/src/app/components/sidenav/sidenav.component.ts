import {Component} from '@angular/core';
import {SidebarService} from '../../services/sidebar.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.scss']
})
export class SidenavComponent {
  constructor(private sidebarService: SidebarService) {}

  public toggleSidebar():void {
    this.sidebarService.toggleSidebar();
  }
}
