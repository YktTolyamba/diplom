from django_filters import rest_framework
from rest_framework import routers, serializers, viewsets

from backend import models


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.User
        fields = ['id', 'url', 'username', 'email', 'is_staff', 'last_name', 'first_name', 'second_name']


class CourseSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.Course
        fields = ['id', 'url', 'name']


class CourseTopicSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.CourseTopic
        fields = ['id', 'url', 'course', 'code', 'name', 'text', 'modified', 'tag']


class TagSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = models.Tag
        fields = ['id', 'name']


class UserViewSet(viewsets.ModelViewSet):
    queryset = models.User.objects.all()
    serializer_class = UserSerializer


class CourseViewSet(viewsets.ModelViewSet):
    queryset = models.Course.objects.all()
    serializer_class = CourseSerializer


class CourseTopicViewSet(viewsets.ModelViewSet):
    queryset = models.CourseTopic.objects.all()
    serializer_class = CourseTopicSerializer
    filter_backends = (rest_framework.DjangoFilterBackend,)
    filter_fields = ('course', 'code', 'name', 'tag')


class TagViewSet(viewsets.ModelViewSet):
    queryset = models.Tag.objects.all()
    serializer_class = TagSerializer


router = routers.DefaultRouter()
router.register(r'users', UserViewSet)
router.register(r'course', CourseViewSet)
router.register(r'course_topic', CourseTopicViewSet)
router.register(r'tag', TagViewSet)
