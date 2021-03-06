import { Component, OnInit, ElementRef } from '@angular/core';
import { JhiAlertService } from 'ng-jhipster';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper, DataInput, DataInputService, DataBundle, DataBundleService } from '../../shared';
import { User, UserService } from '../../shared';
import {map} from "rxjs/operator/map";
import { Subscription } from 'rxjs/Rx';
import { ActivatedRoute } from '@angular/router';

@Component({
    selector: 'jhi-data-input-creation',
    templateUrl: './data-input-creation.component.html',
    providers: [DataInputService, DataBundleService]
})
export class DataInputCreationComponent implements OnInit {

    success: boolean;
    error: boolean;
    errorDataInputExists: boolean;
    dataInput: DataInput;
    users: User[];
    userMap: Map<number, User>;
    private subscription: Subscription;
    dataBundle: DataBundle;
    ownerId: any;
    reviewerId: any;
    currentDate: any;
    ownerIndex: any;
    reviewerIndex: any;

    constructor(
        private alertService: JhiAlertService,
        private dataInputService: DataInputService,
        private userService: UserService,
        private route: ActivatedRoute,
        private dataBundleService: DataBundleService,
    ) {
    }

    ngOnInit() {
        this.success = false;
        this.error = false;
        this.errorDataInputExists = false;
        this.dataInput = {};
        this.loadUsers();
        this.currentDate = new Date();
        this.subscription = this.route.params.subscribe((params) => {
            this.loadDataBundle(params['dataBundleId']);
        });
    }

    ngAfterViewInit() {
    }

    loadDataBundle(dataBundleId) {
        this.dataBundleService.get(dataBundleId).subscribe((dataBundle) => {
            this.dataBundle = dataBundle;
        });
    }


    loadUsers() {
        this.userService.query().subscribe(
            (res: ResponseWrapper) => this.onLoadUsersSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onLoadUsersError(res.json)
        );
    }

    private onLoadUsersSuccess(data, headers) {
        this.users = data;
        this.userMap = new Map<number, User>();
        for (let user of this.users) {
            this.userMap.set(user.id, user);
        }
    }

    private onLoadUsersError(error) {
        this.alertService.error(error.error, error.message, null);
    }

    create() {
        if (this.ownerIndex) {
            const owner = this.userMap.get(parseInt(this.ownerIndex));
            this.dataInput.ownerId = owner.id;
        }
        else {
            this.dataInput.ownerId = this.dataBundle.ownerId;
        }
        if (this.reviewerIndex) {
            const reviewer = this.userMap.get(parseInt(this.reviewerIndex));
            this.dataInput.reviewerId = reviewer.id;
        }
        else {
            this.dataInput.reviewerId = this.dataBundle.reviewerId;
        }

        this.dataInput.dataBundleId = this.dataBundle.id;
        this.dataInput.dataBundleName = this.dataBundle.name;

        this.dataInput.fileName = "a typical file name";
        this.dataInput.fileLocation = "a typical file location";
        this.dataInput.orderIndex = 0;
        this.dataInput.defaultDeadline =this.dataBundle.defaultDeadline;

        this.dataInputService.create(this.dataInput).subscribe(
            response => {
                console.log("success" + response.status);
                this.success = true;
            },
            errorResponse => {
                console.log("error" + errorResponse.status + errorResponse.statusText);
                if (409 == errorResponse.status) {
                    this.errorDataInputExists = true;
                }
                else {
                    this.error = true;
                }
            }
        );
    }
}
