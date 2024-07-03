import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class EnvironmentService {
  isGitHubPages(): boolean {
    return window.location.href.includes('redshift-7.github.io');
  }
}
